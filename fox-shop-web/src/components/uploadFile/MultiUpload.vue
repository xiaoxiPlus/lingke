<template> 
    <div>
        <el-upload
                class="upload-demo"
                action="/wow-api/productAttribute/pms-product-attribute/upload"
                :on-success="handleUploadSuccess"
                :on-remove="handleRemove"
                :headers="myToken"
                :file-list="fileList"
                :on-exceed="handleExceed"
                list-type="picture-card">
          <i class="el-icon-plus"></i>
          <!--  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
        </el-upload>
      <el-dialog :visible.sync="dialogVisible">
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>
    </div>
</template>
<script>
    var token = localStorage.getItem('token') // 要保证取到
    export default {
        name: 'MultiUpload',
        props: {
            //图片属性数组
            value: Array,
            //最大上传图片数量
            maxCount: {
                type: Number,
                default: 5
            }
        },
        data() {
            return {
                dialogVisible: false,
                dialogImageUrl: null,
                myToken: {"Authorization-token": token}
            };
        },
        computed: {
            fileList() {
                let fileList = [];
                for (let i = 0; i < this.value.length; i++) {
                    fileList.push({url: this.value[i]});
                }

                return fileList;
            }
        },
        methods: {
            emitInput(fileList) {
                let value = [];
                for (let i = 0; i < fileList.length; i++) {
                    value.push(fileList[i].url);
                }
                this.$emit('input', value)
            },
            handleRemove(file, fileList) {
                this.emitInput(fileList);
            },
            handlePreview(file) {
                this.dialogVisible = true;
                this.dialogImageUrl = file.url;
            },
            beforeUpload(file) {
                let _self = this;
            },
            handleUploadSuccess(res, file) {
                this.fileList.push({url: res.data});
                this.emitInput(this.fileList);
            },
            handleExceed(files, fileList) {
                this.$message({
                    message: '最多只能上传' + this.maxCount + '张图片',
                    type: 'warning',
                    duration: 1000
                });
            },
        }
    }
</script>
<style>

</style>


