package com.fh.authorization;

import cn.hutool.json.JSONUtil;
import com.fh.SecurityJwtUtils;
import com.fh.login.logs.entity.Logs;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Enumeration;

@Aspect
@Component
public class LogsAop {

    @Resource
    private HttpServletRequest request;
    @Resource
    private SecurityJwtUtils securityJwtUtils;
    @Resource
    private MongoTemplate mongoTemplate;

    @Around(value = "execution(* com.fh.login..*.*(..)) && @annotation(loginAnnotation)")
    public Object loginAround(ProceedingJoinPoint joinPoint,LoginAnnotation loginAnnotation) throws Throwable {
        Object object = null;
        Logs logs = new Logs();
        //获取类路径
        String className = joinPoint.getSignature().getDeclaringTypeName();
        logs.setClassName(className);
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取参数
        String args = JSONUtil.toJsonStr(joinPoint.getArgs());
        logs.setArgs(args);
        logs.setMethodName(methodName);
        logs.setCreateTime(new Date());
        logs.setDescribeInfo(loginAnnotation.value());
        logs.setOperator(getLocalIP());
        long startTime = System.currentTimeMillis();
        //执行方法
        try {
            logs.setType(1);
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            logs.setType(2);
            throwable.printStackTrace();
            logs.setErrorInfo(exception(throwable));
            throw throwable;
        }finally {
            String token = request.getHeader("Authorization-token");
            String username = securityJwtUtils.getUsername(token);
            logs.setRealName(username);
            logs.setRunTime((int) (System.currentTimeMillis()-startTime));
            mongoTemplate.insert(logs);
        }
        return object;
    }

    //获取客户端真实ip
    public String getLocalIP() throws Exception {
        try {
            InetAddress candidateAddress = null;
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            return inetAddr.getHostAddress();
                        } else if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress.getHostAddress();
            }
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private static String exception(Throwable t) throws IOException {
        if (t == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            t.printStackTrace(new PrintStream(baos));
        } finally {
            baos.close();
        }
        return baos.toString();
    }

}
