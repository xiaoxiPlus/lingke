<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-goods"></i> 商品管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                        type="primary"
                        icon="el-icon-delete"
                        class="handle-del mr10"
                        @click="editAttribute"
                >新增商品
                </el-button>
            </div>
            <el-table
                    :data="productData"
                    border
                    class="table"
                    header-cell-class-name="table-header"
            >
                <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
                <el-table-column prop="pic" label="商品图片">
                    <template slot-scope="scope">
                        <img :src="scope.row.pic" alt="暂无" style="height: 60px;width: 60px"/>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="商品名称">
                    <template slot-scope="scope">
                        {{scope.row.name}}<br>品牌：{{scope.row.brandId}}
                    </template>
                </el-table-column>
                <el-table-column prop="price" label="价格/货号">
                    <template slot-scope="scope">
                        价格：￥{{scope.row.price}}<br>货号：{{scope.row.productSn}}
                    </template>
                </el-table-column>
                <el-table-column prop="publishStatus" label="标签">
                    <template slot-scope="scope">
                        上架：
                        <el-switch v-model="scope.row.publishStatus"
                                   :active-value="1"
                                   inactive-value="0"
                                   @change="switchChange(scope.row)"
                        ></el-switch>
                        <br>
                        新品：
                        <el-switch v-model="scope.row.newStatus"
                                   :active-value="1"
                                   inactive-value="0"
                                   @change="switchChange(scope.row)"
                        ></el-switch>
                        <br>
                        推荐：
                        <el-switch v-model="scope.row.recommandStatus"
                                   :active-value="1"
                                   inactive-value="0"
                                   @change="switchChange(scope.row)"
                        ></el-switch>
                        <br>
                    </template>
                </el-table-column>
                <el-table-column prop="sort" label="排序" align="center"></el-table-column>
                <el-table-column label="SKU库存" align="center">
                    <template slot-scope="scope">
                        <el-button type="primary" icon="el-icon-edit" circle></el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="sale" label="销量" align="center"></el-table-column>
                <el-table-column prop="verifyStatus" label="审核状态" align="center">
                    <template slot-scope="scope">
                        <span v-if="scope.row.verifyStatus == 0">未审核</span>
                        <span v-else>已审核</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="200px" align="center">
                    <template slot-scope="scope">
                        <el-button type="default">查看</el-button>
                        <el-button type="default" @click="editAttribute(scope.row.id)">编辑</el-button>
                        <br>
                        <el-button type="default">日志</el-button>
                        <el-button type="danger">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!--分页-->
            <div class="pagination">
                <el-pagination
                        background
                        layout="total, sizes, prev, pager, next, jumper"
                        :current-page="queryCategory.current"
                        :page-size="queryCategory.size"
                        :page-sizes="pagesizes"
                        :total="pageTotal"
                        @current-change="handlePageChange"
                        @size-change="handleSizeChange"
                ></el-pagination>
            </div>
        </div>

    </div>
</template>

<script>
    import {queryProductData, queryProductById} from "../../../../api/product";

    export default {
        name: "Product",
        data() {
            return {
                productData: [],
                queryCategory: {
                    current: 1,
                    size: 5,
                },
                pagesizes: [5, 10, 15, 20],
                pageTotal: 0,
            }
        },

        methods: {
            //新增或修改
            editAttribute(id) {
                if (id != null) {
                        this.$router.push({
                            "path":"/ProductUpdate",
                            "query":{
                                id:id
                            }
                        });
                        return
                }
                this.$router.push("/ProductAdd")
            },
            //上架
            switchChange(row) {

            },
            queryProductData() {
                queryProductData(this.queryCategory).then(result => {
                    this.productData = result.data.data.records;
                    this.pageTotal = result.data.data.total;
                })
            },
            //每页条数数改变
            handleSizeChange(val) {
                this.queryCategory.size = val;
                this.queryCategory.current = 1;
                this.queryProductData();
            },
            //当前页改变
            handlePageChange(val) {
                this.queryCategory.current = val;
                this.queryProductData();
            },
        },

        mounted() {
            this.queryProductData();
        }
    }
</script>

<style scoped>

</style>