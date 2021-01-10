<template>
    <div style="margin-top: 50px">
        <el-form :model="value" ref="productAttrForm" label-width="120px" style="width: 900px;margin: 0 auto"
                 size="small">
            <el-form-item label="属性类型：">
                <el-select v-model="value.productAttributeCategoryId"
                           placeholder="请选择属性类型"
                           @change="handleProductAttrChange">
                    <el-option
                            v-for="item in productAttributeCategoryOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="商品规格：">
                <el-card shadow="never" class="cardBg">
                    <div v-for="(productAttr,idx) in selectProductAttr">
                        {{productAttr.name}}：
                        <el-checkbox-group v-if="productAttr.handAddStatus===0" v-model="selectProductAttr[idx].values">
                            <el-checkbox v-for="item in getInputListArr(productAttr.inputList)" :label="item"
                                         :key="item"
                                         class="littleMarginLeft"></el-checkbox>
                        </el-checkbox-group>

                        <div v-else>
                            <el-checkbox-group v-model="selectProductAttr[idx].values">
                                <div v-for="(item,index) in selectProductAttr[idx].options"
                                     style="display: inline-block"
                                     class="littleMarginLeft">
                                    <el-checkbox :label="item" :key="item"></el-checkbox>
                                    <el-button type="text" class="littleMarginLeft"
                                               @click="handleRemoveProductAttrValue(idx,index)">删除
                                    </el-button>
                                </div>
                            </el-checkbox-group>
                            <el-input v-model.trim="addProductAttrValue" style="width: 160px;margin-left: 10px"
                                      clearable></el-input>
                            <el-button class="littleMarginLeft" @click="handleAddProductAttrValue(idx)">增加</el-button>
                        </div>
                    </div>
                </el-card>
                <el-table style="width: 100%;margin-top: 20px"
                          :data="value.skuStockList"
                          border>
                    <el-table-column
                            v-for="(item,index) in selectProductAttr"
                            :label="item.name"
                            :key="item.id"
                            align="center">
                        <template slot-scope="scope">
                            {{getProductSkuSp(scope.row,index)}}
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="销售价格"
                            width="80"
                            align="center">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.price"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="商品库存"
                            width="80"
                            align="center">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.stock"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="库存预警值"
                            width="80"
                            align="center">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.lowStock"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="SKU编号"
                            align="center">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.skuCode"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="SKU图片"
                            align="center">
                        <template slot-scope="scope">
                            <single-upload-file v-model="scope.row.pic"></single-upload-file>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="操作"
                            width="80"
                            align="center">
                        <template slot-scope="scope">
                            <el-button
                                    type="text"
                                    @click="handleRemoveProductSku(scope.$index, scope.row)">删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-button
                        type="primary"
                        style="margin-top: 20px"
                        @click="handleRefreshProductSkuList">刷新列表
                </el-button>
                <el-button
                        type="primary"
                        style="margin-top: 20px"
                        @click="handleSyncProductSkuPrice">同步价格
                </el-button>
                <el-button
                        type="primary"
                        style="margin-top: 20px"
                        @click="handleSyncProductSkuStock">同步库存
                </el-button>
            </el-form-item>
            <el-form-item label="商品参数：">
                <el-card shadow="never" class="cardBg">
                    <div v-for="(item,index) in selectProductParam" :class="{littleMarginTop:index!==0}">
                        <div class="paramInputLabel">{{item.name}}:</div>
                        <el-select v-if="item.inputType===1" class="paramInput"
                                   v-model="selectProductParam[index].value">
                            <el-option
                                    v-for="item in getParamInputList(item.inputList)"
                                    :key="item"
                                    :label="item"
                                    :value="item">
                            </el-option>
                        </el-select>
                        <el-input v-else class="paramInput" v-model="selectProductParam[index].value"></el-input>
                    </div>
                </el-card>
            </el-form-item>
            <el-form-item label="商品相册：">
                <multipart-upload-file v-model="imgurl"></multipart-upload-file>
            </el-form-item>
            <el-form-item label="规格参数：">
                <el-tabs v-model="activeHtmlName" type="card">
                    <el-tab-pane label="电脑端详情" name="pc">
                        <tinymce :width="595" :height="300" v-model="value.detailHtml"></tinymce>
                    </el-tab-pane>
                    <el-tab-pane label="移动端详情" name="mobile">
                        <tinymce :width="595" :height="300" v-model="value.detailMobileHtml"></tinymce>
                    </el-tab-pane>
                </el-tabs>
            </el-form-item>
            <!--跳转页面-->
            <el-form-item style="text-align: center">
                <el-button size="medium" @click="prevStep">上一步，填写商品促销</el-button>
                <el-button type="primary" size="medium" @click="handleNext">下一步，选择商品关联</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {queryAttributeOptions, queryParamsData} from "../../../../api/procudtAttribute";
    import {queryCateOptions} from "../../../../api/category";
    import multiUploadFile from "../../../uploadFile/MultiUpload"
    import singleUploadFile from "../../../uploadFile/singleUploadFile"

    export default {
        name: "ProductAttrDetail",
        components: {
            "multipartUploadFile": multiUploadFile,
            singleUploadFile
        },
        props: {
            value: Object,
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                /*所有的商品类型*/
                productAttributeCategoryOptions: [],
                /*根据类型id获取的属性*/
                selectProductAttr: [],
                /*手动添加的商品的属性*/
                addProductAttrValue: '',
                /*数据是否加载完成*/
                hasEditCreated: false,
                /*根据类型id获取的参数*/
                selectProductParam: [],
                /*规格参数*/
                activeHtmlName: '',
                imgurl: [],
            }
        },
        computed: {
            /*商品的编号*/
            productId() {
                return this.value.id
            }
        },
        watch: {
            productId: function (newValue) {
                if (!this.isEdit) return;
                if (this.hasEditCreated) return;
                if (newValue === undefined || newValue == null || newValue === 0) return;
                this.selectProductCateValue = [this.value.cateParentId, this.value.productCategoryId];
                this.handleProductAttrChange(this.value.productAttributeCategoryId);
               let pics = this.value.albumPics.split(",");
                for (let i = 0; i < pics.length; i++) {
                    this.imgurl.push(pics[i]);
                }
                this.hasEditCreated = true;
            }
        },

        methods: {
            handleNext() {
                /*商品相册 多图片*/
                this.value.albumPics = this.imgurl.toString();
                this.value.pic = this.imgurl[0];
                /*参数和属性合并保存*/
                this.margeParamAttribute();
                /* $emit 触发父类的方法*/
                this.$emit("nextStep")
            },
            prevStep() {
                this.$emit("prevStep")
            },
            /*参数和属性合并保存*/
            margeParamAttribute() {
                /*每一个参数和属性*/
                this.value.productAttributeValueList = [];
                /*合并商品选中的属性*/
                for (let i = 0; i < this.selectProductAttr.length; i++) {
                    let attr = this.selectProductAttr[i];
                    /*判断是手动输入的  并且有选中的数据*/
                    if (attr.handAddStatus === 1 && attr.options.length > 0 && attr.options != null) {
                        this.value.productAttributeValueList.push({
                            productAttributeId: attr.id,
                            value: this.getOptionStr(attr.options)
                        });
                    }
                }

                /*合并参数*/
                for (let j = 0; j < this.selectProductParam.length; j++) {
                    let params = this.selectProductParam[j];
                    this.value.productAttributeValueList.push({
                        productAttributeId: params.id,
                        /*双向绑定的是 value 值 就取value值*/
                        value: params.value
                    })
                }
            },
            /*把所有的一个集合的数据分成单个的条数*/
            getOptionStr(arr) {
                let str = '';
                for (let i = 0; i < arr.length; i++) {
                    str += arr[i];
                    if (i != arr.length - 1) {
                        str += ',';
                    }
                }
                return str;
            },
            /*参数的下拉*/
            getParamInputList(inputList) {
                return inputList.split(",");
            },
            /*查询属性列表*/
            queryAttriOptions() {
                queryCateOptions().then(result => {
                    let data = result.data.data;
                    let list = [];
                    for (let i = 0; i < data.length; i++) {
                        list.push({"label": data[i].name, "value": data[i].id});
                    }
                    this.productAttributeCategoryOptions = list;
                })

            },
            /*属性列表  值改变事件*/
            handleProductAttrChange(val) {
                /*查询属性列表*/
                queryAttributeOptions(val).then(result => {
                    let data = result.data.data;
                    this.selectProductAttr = [];
                    let values = [];
                    let options = [];
                    for (let i = 0; i < data.length; i++) {
                        //判断是否为修改
                        if (this.isEdit) {
                            /*从列表选择的数据回显*/
                            if (data[i].handAddStatus === 1) {
                                /*手动输入的属性*/
                                options = this.queryParams(data[i].id);
                            }
                            //回显属性
                            values = this.queryAttribute(data[i].name);
                        }
                        this.selectProductAttr.push({
                            id: data[i].id,
                            name: data[i].name,
                            handAddStatus: data[i].handAddStatus,
                            inputList: data[i].inputList,
                            values: values,
                            options: options
                        });
                    }

                });
                this.queryParamsData(val);
            },
            /*回显手动输入的属性*/
            queryParams(id) {
                let attrList = this.value.productAttributeValueList;
                let options = [];
                for (let i = 0; i < attrList.length; i++) {
                    if (id === attrList[i].productAttributeId) {
                        let attrValues = attrList[i].value;
                        let arrList = attrValues.split(",");
                        for (let j = 0; j < arrList.length; j++) {
                            options.push(arrList[j]);
                        }
                    }
                }
                return options;
            },
            /*回显属性*/
            queryAttribute: function (name) {
                let attrSet = new Set();
                let skuList = this.value.skuStockList;
                for (let i = 0; i < skuList.length; i++) {
                    /*回显数据中的每一个JSON串*/
                    let spData = skuList[i].spData;
                    /*将JSON串转化为数组*/
                    let data = JSON.parse(spData);
                    /*循环其中的每一个元素*/
                    for (let j = 0; j < data.length; j++) {
                        if (name === data[j].key) {
                            attrSet.add(data[j].value);
                        }
                    }
                }
                return Array.from(attrSet);
            },
            /*查询参数*/
            queryParamsData(val) {
                queryParamsData(val).then(result => {
                    let data = result.data.data;
                    this.selectProductParam = [];
                    for (let i = 0; i < data.length; i++) {
                        let value = null;
                        if (this.isEdit) {
                            value = this.getParams(data[i].id);
                        }
                        this.selectProductParam.push({
                            id: data[i].id,
                            name: data[i].name,
                            value: value,
                            inputType: data[i].inputType,
                            inputList: data[i].inputList,
                        })
                    }
                })
            },
            /*回显参数*/
            getParams(id) {
                let attrList = this.value.productAttributeValueList;
                for (let i = 0; i < attrList.length; i++) {
                    if (id === attrList[i].productAttributeId) {
                        return attrList[i].value;
                    }
                }
            },
            /*获取复选框的值*/
            getInputListArr(inputList) {
                return inputList.split(',');
            },
            /*手动添加属性*/
            handleAddProductAttrValue(idx) {
                let options = this.selectProductAttr[idx].options;
                if (this.addProductAttrValue === null || this.addProductAttrValue === "") {
                    this.$message({
                        message: '属性值不能为空',
                        type: 'warning',
                        duration: 1000
                    });
                    return;
                }
                if (options.indexOf(this.addProductAttrValue) != -1) {
                    this.$message({
                        message: '属性值不能重复',
                        type: 'warning',
                        duration: 1000
                    });
                    return;
                }
                options.push(this.addProductAttrValue);
                this.addProductAttrValue = null;
            },
            /*删除手动添加的属性*/
            handleRemoveProductAttrValue(idx, index) {
                this.selectProductAttr[idx].options.splice(index, 1);
            },
            /*刷新表格*/
            handleRefreshProductSkuList() {
                this.$confirm('刷新列表将导致sku信息重新生成，是否要刷新', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    /*this.refreshProductAttrPics();*/
                    this.refreshProductSkuList();
                });
            },
            /*刷新sku表格*/
            refreshProductSkuList() {
                /*将表格中展示的数据都清空*/
                this.value.skuStockList = [];
                /*sku的属性  价格 库存  图片等*/
                let skuList = this.value.skuStockList;
                /*判断有几个可以操作的属性*/
                if (this.selectProductAttr.length === 1) {
                    /*第一个属性 因为只有一个*/
                    let list = this.selectProductAttr[0];
                    /*如果没有被选中的属性，中断方法*/
                    if (list.length === 0) {
                        this.$message({
                            message: '还没有选择属性',
                            type: 'warning',
                            duration: 1000
                        });
                        return;
                    }
                    /*循环所有被选中属性的选项*/
                    for (let i = 0; i < list.values.length; i++) {
                        /*key值为spData 因为上边根据spData取值 将value转成JSON串格式*/
                        skuList.push({
                            spData: JSON.stringify([{key: list.name, value: list.values[i]}])
                        })
                    }
                    /*this.$notify*/
                    /*两个属性*/
                } else if (this.selectProductAttr.length === 2) {
                    /*第一个属性*/
                    let list0 = this.selectProductAttr[0];
                    /*第二个属性*/
                    let list1 = this.selectProductAttr[1];
                    if (list0.values.length === 0 || list1.values.length === 0) {
                        this.$message({
                            message: '每一行的属性至少选择一个',
                            type: 'warning',
                            duration: 1000
                        });
                        return;
                    }
                    /*两个属性循环两次，所有的属性组合*/
                    for (let i = 0; i < list0.values.length; i++) {
                        for (let j = 0; j < list1.values.length; j++) {
                            let spData = [];
                            /*  name 属性的名称    values[i] 属性下的选项*/
                            spData.push({key: list0.name, value: list0.values[i]});
                            spData.push({key: list1.name, value: list1.values[j]});
                            skuList.push({
                                spData: JSON.stringify(spData)
                            })
                        }
                    }
                    /*最多三个属性*/
                } else {
                    /*第一个属性*/
                    let list0 = this.selectProductAttr[0];
                    /*第二个属性*/
                    let list1 = this.selectProductAttr[1];
                    /*第三个属性*/
                    let list2 = this.selectProductAttr[2];
                    if (list0.values.length === 0 || list1.values.length === 0 || list2.values.length === 0) {
                        this.$message({
                            message: '每一行的属性至少选择一个',
                            type: 'warning',
                            duration: 1000
                        });
                        return;
                    }

                    for (let i = 0; i < list0.values.length; i++) {
                        for (let j = 0; j < list1.values.length; j++) {
                            for (let g = 0; g < list2.values.length; g++) {

                                let spData = [];
                                /*  name 属性的名称    values[i] 属性下的选项*/
                                spData.push({key: list0.name, value: list0.values[i]});
                                spData.push({key: list1.name, value: list1.values[j]});
                                spData.push({key: list2.name, value: list2.values[g]});
                                skuList.push({
                                    spData: JSON.stringify(spData)
                                })

                            }
                        }
                    }
                }
            },
            /*取出值的方法*/
            getProductSkuSp(row, index) {
                /*取出值 将JSON串转成对象,index是table中每个需要渲染的单元格*/
                let spData = JSON.parse(row.spData);
                if (spData != null && index < spData.length) {
                    return spData[index].value;
                } else {
                    return null;
                }

            },
            /*同步价格*/
            handleSyncProductSkuPrice() {
                this.$confirm('将同步第一个sku的价格到所有sku,是否继续', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    if (this.value.skuStockList !== null && this.value.skuStockList.length > 0) {
                        let tempSkuList = this.value.skuStockList;
                        let price = this.value.skuStockList[0].price;
                        for (let i = 0; i < tempSkuList.length; i++) {
                            tempSkuList[i].price = price;
                        }
                        this.value.skuStockList = [];
                        this.value.skuStockList = this.value.skuStockList.concat(tempSkuList, this.value.skuStockList);
                    }
                })
            },
            /*同步库存及库存余量*/
            handleSyncProductSkuStock() {
                this.$confirm('将同步第一个sku的库存及库存预警值到所有sku,是否继续', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    if (this.value.skuStockList !== null && this.value.skuStockList.length > 0) {
                        let tempSkuList = [];
                        tempSkuList = tempSkuList.concat(tempSkuList, this.value.skuStockList);
                        let stock = this.value.skuStockList[0].stock;
                        let lowStock = this.value.skuStockList[0].lowStock;
                        for (let i = 0; i < tempSkuList.length; i++) {
                            tempSkuList[i].stock = stock;
                            tempSkuList[i].lowStock = lowStock;
                        }
                        this.value.skuStockList = [];
                        this.value.skuStockList = this.value.skuStockList.concat(this.value.skuStockList, tempSkuList);
                    }
                })

            }
        },

        mounted() {
            this.queryAttriOptions();
        }
    }
</script>

<style scoped>
    .littleMarginLeft {
        margin-left: 10px;
    }

    .littleMarginTop {
        margin-top: 10px;
    }

    .paramInput {
        width: 250px;
    }

    .paramInputLabel {
        display: inline-block;
        width: 100px;
        text-align: right;
        padding-right: 10px
    }

    .cardBg {
        background: #F8F9FC;
    }

</style>