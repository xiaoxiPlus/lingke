<template>
  <div class="wrapper">
    <v-head></v-head>
    <v-sidebar></v-sidebar>
    <div class="content-box" :class="{'content-collapse':collapse}">
      <v-tags></v-tags>
      <div class="content">
        <transition name="move" mode="out-in">
          <keep-alive :include="tagsList">
            <router-view></router-view>
          </keep-alive>
        </transition>
        <el-backtop target=".content"></el-backtop>
      </div>
    </div>
  </div>
</template>

<script>
import vHead from './Header.vue';
import vSidebar from './Sidebar.vue';
import vTags from './Tags.vue';
import bus from './bus';
import {checkToken} from "@/api/ums/admin";
import router from "@/router";

export default {
  data() {
    return {
      tagsList: [],
      collapse: false
    };
  },
  components: {
    vHead,
    vSidebar,
    vTags
  },
  created() {
    bus.$on('collapse-content', msg => {
      this.collapse = msg;
    });

    // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
    bus.$on('tags', msg => {
      let arr = [];
      for (let i = 0, len = msg.length; i < len; i++) {
        msg[i].name && arr.push(msg[i].name);
      }
      this.tagsList = arr;
    });
  },
  methods: {
    initWebSocket() {
      //验证token值是否有效
      this.checkToken();
      var self = this;
      var webSocket = null;
      /*取出token*/
      var token = localStorage.getItem("token");
      //判断当前浏览器是否支持webSocket
      if ("WebSocket" in window) {
        webSocket = new WebSocket("ws://localhost:9080/socket", [token]);
      } else {
        alert("该浏览器不支持webSocket");
        return;
      }
      webSocket.onopen = function (event) {

      }
      webSocket.onmessage = function (event) {
        /*判断后台传来的状态，弹出相应的提示*/
       let data = JSON.parse(event.data);
      if (data.code==100){
        self.$notify({
          title: '异地登录提示',
          position: 'bottom-right',
          dangerouslyUseHTMLString: true,
          message: '<strong style="font-size: 14px;color: #DD4A68">' + data.msg + '</strong>'
        });
      }else if(data.code == 200){
        self.$notify({
          title: '登陆提示',
          position: 'bottom-right',
          duration:1500,
          dangerouslyUseHTMLString: true,
          message: '<strong style="font-size: 7px;color: #00d1b2">' + data.msg + '</strong>'
        });
      }else if(data.code == 300){
        self.$notify({
          title: '登陆提示',
          position: 'bottom-right',
          duration:1500,
          dangerouslyUseHTMLString: true,
          message: '<strong style="font-size: 7px;color: #00d1b2">' + data.msg + '</strong>'
        });
      }
      }
      webSocket.onclose = function (event) {

      }
      webSocket.onerror = function () {
      }
    },
    checkToken(){
      var token = localStorage.getItem("token");
      /*checkToken(token).then(result=>{
        var data = result.data.data;
          if (data == 0){
            router.replace("/login");
          }
      })*/
    }
  },
  mounted() {
    this.initWebSocket();
  }
};
</script>
