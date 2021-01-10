<template>
    <el-card class="form-container" shadow="never">
        <el-steps :active="active" finish-status="success" align-center>
            <el-step title="填写商品信息"></el-step>
            <el-step title="填写商品促销"></el-step>
            <el-step title="填写商品属性"></el-step>
            <el-step title="选择商品关联"></el-step>
        </el-steps>
        <!--基本信息-->
        <product-info-detail
                v-show="showStatus[0]"
                :is-edit="isEdit"
                v-model="productParam"
                @nextStep="nextStep">
        </product-info-detail>

        <!--促销信息-->
        <product-sale-detail
                v-show="showStatus[1]"
                :is-edit="isEdit"
                v-model="productParam"
                @prevStep="prevStep"
                @nextStep="nextStep">

        </product-sale-detail>

        <!--属性-->
        <product-attr-detail
                v-show="showStatus[2]"
                :is-edit="isEdit"
                v-model="productParam"
                @prevStep="prevStep"
                @nextStep="nextStep">

        </product-attr-detail>

        <!--关联信息-->
        <product-relation-detail
                v-show="showStatus[3]"
                :is-edit="isEdit"
                v-model="productParam"
                @prevStep="prevStep">

        </product-relation-detail>

    </el-card>
</template>

<script>
    import ProductAttrDetail from './ProductAttrDetail.vue'
    import ProductInfoDetail from './ProductInfoDetail.vue'
    import ProductRelationDetail from './ProductRelationDetail.vue'
    import ProductSaleDetail from './ProductSaleDetail.vue'
    import {queryProductById} from "../../../../api/product";

    const defaultProductParam = {
        albumPics: '',
        brandId: null,
        brandName: '',
        deleteStatus: 0,
        description: '',
        detailDesc: '',
        detailHtml: '',
        detailMobileHtml: '',
        detailTitle: '',
        feightTemplateId: 0,
        flashPromotionCount: 0,
        flashPromotionId: 0,
        flashPromotionPrice: 0,
        flashPromotionSort: 0,
        giftPoint: 0,
        giftGrowth: 0,
        keywords: '',
        lowStock: 0,
        name: '',
        newStatus: 0,
        note: '',
        originalPrice: 0,
        pic: '',
        //会员价格{memberLevelId: 0,memberPrice: 0,memberLevelName: null}
        memberPriceList: [],
        //商品满减
        productFullReductionList: [{fullPrice: 0, reducePrice: 0}],
        //商品阶梯价格
        productLadderList: [{count: 0, discount: 0, price: 0}],
        previewStatus: 0,
        price: 0,
        /*商品属性id*/
        productAttributeCategoryId: null,
        //商品属性相关{productAttributeId: 0, value: ''}
        productAttributeValueList: [],
        //商品sku库存信息{lowStock: 0, pic: '', price: 0, sale: 0, skuCode: '', spData: '', stock: 0}
        skuStockList: [],
        //商品的属性值和参数列表
        stuParamAttriList: [],
        //商品相关专题{subjectId: 0}
        subjectProductRelationList: [],
        //商品相关优选{prefrenceAreaId: 0}
        prefrenceAreaProductRelationList: [],
        /*一级id  parentId = 0的*/
        cateParentId: null,
        /*二级id*/
        productCategoryId: null,
        productCategoryName: '',
        productSn: '',
        promotionEndTime: '',
        promotionPerLimit: 0,
        promotionPrice: null,
        promotionStartTime: '',
        /*控制展示哪一个优惠页面*/
        promotionType: 0,
        publishStatus: 0,
        recommandStatus: 0,
        sale: 0,
        serviceIds: '',
        sort: 0,
        stock: 0,
        subTitle: '',
        unit: '',
        usePointLimit: 0,
        verifyStatus: 0,
        weight: 0
    };

    export default {
        name: "ProductDetail",
        components: {ProductAttrDetail, ProductInfoDetail, ProductRelationDetail, ProductSaleDetail},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                /*true为显示页面*/
                showStatus: [true, false, false, false],
                /*默认绑定第一页*/
                active: 0,
                /*对表单的值进行初始化*/
                productParam: Object.assign({}, defaultProductParam),
            }
        },
        watch: {
            '$route'(to, from) {
                if (this.isEdit) {
                    this.productParam = Object.assign({}, defaultProductParam)
                    this.$router.go(0);
                }
            },
        },
        methods: {
            /*隐藏所有子页面*/
            hideAll() {
                for (let i = 0; i < this.showStatus.length; i++) {
                    this.showStatus[i] = false;
                }
            },
            //下一步
            nextStep() {
                /*下一步将之前的所有页面隐藏，active+1 展示下一个页面*/
                if (this.active < this.showStatus.length - 1) {
                    this.active++;
                    this.hideAll();
                    /*展示第 active 个页面*/
                    this.showStatus[this.active] = true;
                }
            },
            //上一步
            prevStep() {
                if (this.active > 0) {
                    this.active--;
                    this.hideAll();
                    this.showStatus[this.active] = true;
                }
            },
        },
        created() {
            if (this.isEdit) {
                queryProductById(this.$route.query.id).then(result => {
                    this.productParam = result.data.data;
                })
            }
        },
        mounted() {

        }
    }
</script>

<style scoped>

</style>