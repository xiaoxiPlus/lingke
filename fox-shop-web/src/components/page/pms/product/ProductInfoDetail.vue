<template>
    <div>
        <div style="margin-top: 50px">
            <el-form :model="value" ref="productInfoForm" label-width="120px" style="width: 900px;margin: 0 auto"
                     size="small">
                <el-form-item label="商品分类：" prop="productCategoryId">
                    <el-cascader
                            v-model="selectProductCateValue"
                            :options="productCateOptions">
                    </el-cascader>
                </el-form-item>
                <el-form-item label="商品名称：" prop="name">
                    <el-input v-model="value.name"></el-input>
                </el-form-item>
                <el-form-item label="副标题：" prop="subTitle">
                    <el-input v-model="value.subTitle"></el-input>
                </el-form-item>
                <el-form-item label="商品品牌：" prop="brandId">
                    <el-select
                            v-model="value.brandId"
                            placeholder="请选择品牌">
                        <el-option
                                v-for="item in brandOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="商品介绍：">
                    <el-input
                            :autoSize="true"
                            v-model="value.description"
                            type="textarea"
                            placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="商品货号：">
                    <el-input v-model="value.productSn"></el-input>
                </el-form-item>
                <el-form-item label="商品售价：">
                    <el-input v-model="value.price"></el-input>
                </el-form-item>
                <el-form-item label="市场价：">
                    <el-input v-model="value.originalPrice"></el-input>
                </el-form-item>
                <el-form-item label="商品库存：">
                    <el-input v-model="value.stock"></el-input>
                </el-form-item>
                <el-form-item label="计量单位：">
                    <el-input v-model="value.unit"></el-input>
                </el-form-item>
                <el-form-item label="商品重量：">
                    <el-input v-model="value.weight" style="width: 300px"></el-input>
                    <span style="margin-left: 20px">克</span>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="value.sort"></el-input>
                </el-form-item>
                <el-form-item style="text-align: center">
                    <el-button type="primary" size="medium" @click="handleNext">下一步，填写商品促销
                    </el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>

<script>
    import {queryProcudtCategoryOptions} from '../../../../api/procudtCategory'
    import {queryBrandOptions} from "../../../../api/brand";

    export default {
        name: "ProductInfoDetail.vue",
        props: {
            /*获取前台传来的值*/
            value: Object,
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                hasEditCreated:false,
                selectProductCateValue: [],
                brandOptions: [],
                productCateOptions: []
            }
        },
        computed:{
            //商品的编号
            productId(){
                return this.value.id;
            }
        },
        watch: {
            productId:function(newValue){
                if(!this.isEdit)return;
                if(this.hasEditCreated)return;
                if(newValue===undefined||newValue==null||newValue===0)return;
                this.handleEditCreated();
            },
            selectProductCateValue:function (newValue) {
                if (newValue!=null && newValue.length>1){
                    this.value.cateParentId = newValue[0];
                    this.value.productCategoryId = newValue[1]
                }else{
                    this.value.cateParentId = null;
                    this.value.productCategoryId = null
                }
            }
        },
        methods: {
            /*下一步*/
            handleNext() {
                /* $emit 触发父类的方法*/
                this.$emit("nextStep")
            },
            handleEditCreated(){
                if(this.value.productCategoryId!=null){
                    this.selectProductCateValue=[this.value.cateParentId,this.value.productCategoryId];
                }
                this.hasEditCreated=true;
            },
            /*查询商品分类 二级联动*/
            queryProductCateOptions() {
                queryProcudtCategoryOptions().then(result => {
                    this.productCateOptions = result.data.data;
                })
            },
            /*查询品牌*/
            queryBrandOptions() {
                queryBrandOptions().then(result => {
                    this.brandOptions = result.data.data;
                })
            }
        },

        mounted() {
            this.queryProductCateOptions();
            this.queryBrandOptions();
        }
    }
</script>

<style scoped>

</style>