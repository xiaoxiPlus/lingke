module.exports = {
    baseUrl: './',
    assetsDir: 'static',
    productionSourceMap: false,
    devServer: {
        proxy: {
            '/wow-api':{
                target:'http://192.168.230.1:9080',
                changeOrigin:false,
                pathRewrite:{
                    '^/wow-api':'/'
                }
            }
        }
    }
}