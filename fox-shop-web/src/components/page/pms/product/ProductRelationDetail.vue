<template>
    <div style="margin-top: 50px">
        <el-form :model="value"
                 label-width="120px"
                 style="width: 680px"
                 size="small">
            <el-form-item style="text-align: center">
                <el-button size="medium" @click="prevStep">上一步，填写商品属性</el-button>
                <el-button type="primary" size="medium" @click="handleFinishCommit">完成，提交商品</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {saveOrUpdateProduct} from "../../../../api/product";

    export default {
        name: "ProductRelationDetail",
        props: {
            value:Object,
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
            }
        },

        methods: {
            handleFinishCommit() {
                saveOrUpdateProduct(this.value).then(result=>{
                    this.$message.success("新增商品成功");
                    this.$router.push("/Product")
                });
            },
            prevStep() {
                this.$emit("prevStep")
            },
        },

        mounted() {
        }
    }
</script>

<style scoped>

</style>