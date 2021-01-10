<template>
    <div style="margin-top: 50px">
        <el-form :model="value" label-width="120px" style="width: 900px;margin: 0 auto"
                 size="small">
            <el-form-item label="赠送积分：">
                <el-input v-model="value.giftPoint"></el-input>
            </el-form-item>
            <el-form-item label="赠送成长值：">
                <el-input v-model="value.giftGrowth"></el-input>
            </el-form-item>
            <el-form-item label="积分购买限制：">
                <el-input v-model="value.usePointLimit"></el-input>
            </el-form-item>
            <el-form-item label="预告商品：">
                <el-switch
                        v-model="value.previewStatus"
                        :active-value="1"
                        :inactive-value="0">
                </el-switch>
            </el-form-item>
            <el-form-item label="商品上架：">
                <el-switch
                        v-model="value.publishStatus"
                        :active-value="1"
                        :inactive-value="0">
                </el-switch>
            </el-form-item>
            <el-form-item label="商品推荐：">
                <span style="margin-right: 10px">新品</span>
                <el-switch
                        v-model="value.newStatus"
                        :active-value="1"
                        :inactive-value="0">
                </el-switch>
                <span style="margin-left: 10px;margin-right: 10px">推荐</span>
                <el-switch
                        v-model="value.recommandStatus"
                        :active-value="1"
                        :inactive-value="0">
                </el-switch>
            </el-form-item>
            <el-form-item label="服务保证：">
                <el-checkbox-group v-model="selectServiceList">
                    <el-checkbox :label="1">无忧退货</el-checkbox>
                    <el-checkbox :label="2">快速退款</el-checkbox>
                    <el-checkbox :label="3">免费包邮</el-checkbox>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item label="详细页标题：">
                <el-input v-model="value.detailTitle"></el-input>
            </el-form-item>
            <el-form-item label="详细页描述：">
                <el-input v-model="value.detailDesc"></el-input>
            </el-form-item>
            <el-form-item label="商品关键字：">
                <el-input v-model="value.keywords"></el-input>
            </el-form-item>
            <el-form-item label="商品备注：">
                <el-input v-model="value.note" type="textarea" :autoSize="true"></el-input>
            </el-form-item>
            <el-form-item label="选择优惠方式：">
                <el-radio-group v-model="value.promotionType" size="small">
                    <el-radio-button :label="0">无优惠</el-radio-button>
                    <el-radio-button :label="1">特惠促销</el-radio-button>
                    <el-radio-button :label="2">会员价格</el-radio-button>
                    <el-radio-button :label="3">阶梯价格</el-radio-button>
                    <el-radio-button :label="4">满减价格</el-radio-button>
                </el-radio-group>
            </el-form-item>
            <el-form-item v-show="value.promotionType===1">
                <div>
                    开始时间：
                    <el-date-picker
                            v-model="value.promotionStartTime"
                            value-format="timestamp"
                            type="datetime"
                            :picker-options="pickerStartTime(value.promotionEndTime)"
                            placeholder="选择开始时间">
                    </el-date-picker>
                </div>
                <div class="littleMargin">
                    结束时间：
                    <el-date-picker
                            v-model="value.promotionEndTime"
                            value-format="timestamp"
                            type="datetime"
                            :picker-options="pickerEndTime(value.promotionStartTime)"
                            placeholder="选择结束时间">
                    </el-date-picker>
                </div>
                <div class="littleMargin">
                    促销价格：
                    <el-input style="width: 220px" v-model="value.promotionPrice" placeholder="输入促销价格"></el-input>
                </div>

            </el-form-item>
            <el-form-item v-show="value.promotionType===2">
                <div v-for="(item, index) in value.memberPriceList" :class="{littleMargin:index!==0}">
                    {{item.memberLevelName}}：
                    <el-input v-model="item.memberPrice" style="width: 200px"></el-input>
                </div>
            </el-form-item>
            <el-form-item v-show="value.promotionType===3">
                <el-table :data="value.productLadderList"
                          style="width: 80%" border>
                    <el-table-column
                            label="数量"
                            align="center"
                            width="120">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.count"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="折扣"
                            align="center"
                            width="120">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.discount"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column
                            align="center"
                            label="操作">
                        <template slot-scope="scope">
                            <el-button type="text" @click="handleRemoveProductLadder(scope.$index, scope.row)">删除
                            </el-button>
                            <el-button type="text" @click="handleAddProductLadder(scope.$index, scope.row)">添加
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form-item>
            <el-form-item v-show="value.promotionType===4">
                <el-table :data="value.productFullReductionList"
                          style="width: 80%" border>
                    <el-table-column
                            label="满"
                            align="center"
                            width="120">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.fullPrice"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column
                            label="立减"
                            align="center"
                            width="120">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.reducePrice"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column
                            align="center"
                            label="操作">
                        <template slot-scope="scope">
                            <el-button type="text" @click="handleRemoveFullReduction(scope.$index, scope.row)">删除
                            </el-button>
                            <el-button type="text" @click="handleAddFullReduction(scope.$index, scope.row)">添加
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form-item>
            <el-form-item style="text-align: center">
                <el-button size="medium" @click="prevStep">上一步，填写商品信息</el-button>
                <el-button type="primary" size="medium" @click="handleNext">下一步，填写商品属性</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {queryMemberOptions} from "../../../../api/ums_member";

    export default {
        name: "ProductSaleDetail",
        props: {
            value: Object,
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                pickerStartTime(endTime) {
                    return {
                        disabledDate(time) {
                            let endDateVal = endTime;
                            if (endDateVal) {
                                return (time.getTime() > new Date(endDateVal).getTime());
                            } else {
                                return time.getTime() < Date.now();
                            }
                        }
                    }

                },
                pickerEndTime(startTime) {
                    return {
                        disabledDate: time => {
                            let beginDateVal = startTime;
                            if (beginDateVal) {
                                return (
                                    time.getTime() <
                                    new Date(beginDateVal).getTime()
                                );
                            } else {
                                return (
                                    Date.now() > time.getTime()
                                );
                            }
                        }
                    }
                },
                defaultStatus: 0
            }
        },
        computed: {
            selectServiceList: {
                get() {
                    let list = [];
                    if (this.value.serviceIds === undefined || this.value.serviceIds == null || this.value.serviceIds === "") {
                        return list;
                    }
                    let ids = this.value.serviceIds.split(",");
                    for (let i = 0; i < ids.length; i++) {
                        list.push(Number(ids[i]))
                    }
                    return list;
                },
                set(newValue) {
                    let serviceIds = "";
                    if (newValue != null && newValue.length > 0) {
                        for (let i = 0; i < newValue.length; i++) {
                            serviceIds += newValue[i] + ",";
                        }
                        if (serviceIds.endsWith(",")) {
                            serviceIds = serviceIds.substr(0, serviceIds.length - 1);
                        }
                        this.value.serviceIds = serviceIds;
                    } else {
                        this.value.serviceIds = '';
                    }

                }
            }

        },

        methods: {
            handleNext() {
                /* $emit 触发父类的方法*/
                this.$emit("nextStep")
            },
            prevStep() {
                this.$emit("prevStep")
            },
            /*查询会员等级*/
            queryMemberOptions() {
                queryMemberOptions(this.defaultStatus).then(result => {
                    let data = result.data.data;
                    let list = [];
                    for (let i = 0; i < data.length; i++) {
                        if (this.isEdit) {
                            if (this.value.memberPriceList[i]==null){
                                list.push({
                                    memberLevelName: data[i].name,
                                    memberLevelId: data[i].id,
                                    memberPrice: null
                                })
                            }else{
                                list.push({
                                    memberLevelName: data[i].name,
                                    memberLevelId: data[i].id,
                                    memberPrice: this.value.memberPriceList[i].memberPrice
                                })
                            }
                        } else {
                            list.push({memberLevelName: data[i].name, memberLevelId: data[i].id, memberPrice: null})
                        }
                    }
                    this.value.memberPriceList = list;
                });

            },
            /*增加阶梯价格*/
            handleAddProductLadder(index, row) {
                let data = this.value.productLadderList;
                if (data.length < 3) {
                    data.push({count: 0, discount: 0, price: 0});
                } else {
                    this.$message.error("阶梯价格最多设置三个");
                    return false;
                }
            },
            /*删除一行阶梯价格*/
            handleRemoveProductLadder(index, row) {
                let data = this.value.productLadderList;
                if (data.length == 1) {
                    data.pop();
                    data.push({count: 0, discount: 0, price: 0});
                } else {
                    data.splice(index, 1);
                }
            },
            /*增加一条满减*/
            handleAddFullReduction(index, row) {
                let data = this.value.productFullReductionList;
                if (data.length < 3) {
                    data.push({fullPrice: 0, reducePrice: 0});
                } else {
                    this.$message.error("满减价格最多设置三个");
                    return false;
                }
            },
            /*删除一条满减*/
            handleRemoveFullReduction(index, row) {
                let data = this.value.productFullReductionList;
                if (data.length == 1) {
                    data.pop();
                    data.push({fullPrice: 0, reducePrice: 0})
                } else {
                    data.splice(index, 1);
                }
            }

        },

        mounted() {
            if (this.isEdit) {
                /*回显复选框*/
                if (this.value.serviceIds !== '' && this.value.serviceIds.length !== 0 && this.value.serviceIds !== null) {
                    let list = this.value.serviceIds.split(",");
                    for (let i = 0; i < list.length; i++) {
                        this.selectServiceList.push(list[i]);
                    }
                }
            }
            this.queryMemberOptions();
        }
    }
</script>

<style scoped>

</style>