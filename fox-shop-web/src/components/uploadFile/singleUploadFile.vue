<template>
    <el-upload
            style="margin-right: 1200px;width: 50%"
            class="avatar-uploader"
            action="/wow-api/productCategory/pms-product-category/upload"
            :show-file-list="false"
            :headers="myToken"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
        <img v-if="imgUrl" :src="imgUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
</template>

<script>
    let token = localStorage.getItem("token");
    export default {
        name: "singleUploadFile",
        props: {
            value: String
        },
        computed: {
            imgUrl() {
                return this.value;
            },
            myToken() {
                return {
                    'Authorization-token': token
                }
            }
        },
        data() {
            return {}
        },

        methods: {
            handleAvatarSuccess(res, file) {
                this.imgUrl = res.data;
                this.emitInput(res.data);
            },
            beforeAvatarUpload(file) {

                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            emitInput(val) {
                this.$emit("input", val)
            }
        },

        mounted() {

        }
    }
</script>

<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 80px;
        height: 80px;
        line-height: 80px;
        text-align: center;
    }
    .avatar {
        width: 80px;
        height: 80px;
        display: block;
    }
</style>
