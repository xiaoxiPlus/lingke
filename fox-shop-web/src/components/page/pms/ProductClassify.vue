<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 商品展示
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">

                <el-input v-model="query.name" placeholder="分类名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="queryData">搜索</el-button>
                <el-button type="info" plain style="float: right" size="medium" icon="el-icon-lx-add"
                           @click="addProduct">添加一条数据
                </el-button>
            </div>
            <el-table
                    :data="tableData"
                    border
                    class="table"
                    header-cell-class-name="table-header"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="商品名称"></el-table-column>
                <el-table-column label="级别">
                    <template slot-scope="scope">
                        <span v-if="scope.row.level == 0">一级</span>
                        <span v-else>二级</span>
                    </template>
                </el-table-column>
                <el-table-column prop="productCount" label="商品余量"></el-table-column>
                <el-table-column prop="productUnit" label="数量单位"></el-table-column>
                <el-table-column prop="icon" label="分类图标" width="80px">
                    <template slot-scope="scope">
                        <img :src="scope.row.icon" alt="暂无" style="height: 60px;width: 60px"/>
                    </template>

                </el-table-column>

                <el-table-column label="导航栏">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.navStatus"
                                   :active-value="1"
                                   inactive-value="0"
                                   @change="switchChange(scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="显示状态">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.showStatus"
                                   :active-value="1"
                                   inactive-value="0"
                                   @change="statusChange(scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column prop="sort" label="排序"></el-table-column>


                <el-table-column label="设置" width="150" align="center">
                    <template slot-scope="scope">
                        <el-button
                                v-if="query.level == 0"
                                size="small"
                                icon="el-icon-edit"
                                @click="checkSubordinate(scope.$index, scope.row)"
                        >查看下级
                        </el-button>
                        <el-button
                                v-else
                                size="small"
                                icon="el-icon-edit"
                                @click="upSubordinate()"
                        >返回上级
                        </el-button>
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="150" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="primary"
                                size="small"
                                icon="el-icon-edit"
                                @click="editPro(scope.$index, scope.row)"
                        >编 辑
                        </el-button>
                    </template>
                </el-table-column>

            </el-table>
            <div class="pagination">
                <el-pagination
                        background
                        layout="total, sizes, prev, pager, next, jumper"
                        :current-page="query.current"
                        :page-size="query.size"
                        :page-sizes="pagesizes"
                        :total="pageTotal"
                        @current-change="handlePageChange"
                        @size-change="handleSizeChange"
                ></el-pagination>
            </div>
        </div>

        <!-- 新增修改弹出框 -->
        <el-dialog title="新增分类" :visible.sync="editVisible" width="40%" @close='close'>
            <el-form ref="form" :model="productForm" label-width="70px">
                <el-form-item label="分类名称">
                    <el-input v-model="productForm.name"></el-input>
                </el-form-item>
                <el-form-item label="上级分类">
                    <el-select v-model="productForm.parentId">
                        <el-option label="没有上级分类" :value="0"></el-option>
                        <el-option
                                v-for="item in options"
                                :value="item.id"
                                :label="item.name"
                                :key="item.id"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="数量单位">
                    <el-input v-model="productForm.productUnit"></el-input>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="productForm.sort"></el-input>
                </el-form-item>
                <el-form-item label="是否显示">
                    <el-switch v-model="productForm.navStatus"
                               :active-value="1"
                               inactive-value="0"
                    ></el-switch>
                </el-form-item>
                <el-form-item label="是否显示在导航栏">
                    <el-switch v-model="productForm.showStatus"
                               :active-value="1"
                               inactive-value="0"
                    ></el-switch>
                </el-form-item>
                <el-form-item label="分类图标" prop="icon">
                    <single-upload-file v-model="productForm.icon"></single-upload-file>
                </el-form-item>
                <el-form-item label="关键字">
                    <el-input v-model="productForm.keywords"></el-input>
                </el-form-item>
                <el-form-item label="分类描述">
                    <el-input v-model="productForm.description"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button v-if="productForm.id == '' " type="primary" @click="saveEdit">确 定</el-button>
                <el-button v-else type="primary" @click="editProduct">修 改</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    import {
        productTable,
        updateNavstatus,
        updateStatus,
        addProduct,
        queryProductOptions,
        queryProduct,
        editProduct
    } from "../../../api/procudtCategory";
    import singleUploadFile from '@/components/uploadFile/singleUploadFile';

    export default {
        name: 'ProductClassify',
        components: {
            singleUploadFile
        },
        data() {
            return {
                query: {
                    name: '',
                    current: 1,
                    size: 10,
                    level: 0,
                },
                productForm: {
                    id: '',
                    name: '',
                    parentId: 0,
                    productUnit: '',
                    sort: 0,
                    navStatus: 1,
                    showStatus: 1,
                    icon: '',
                    keywords: '',
                    description: ''
                },
                imgUrl: '',
                options: {},
                pagesizes: [5, 10, 15, 20],
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                pageTotal: 0,
                form: {},
                idx: -1,
                id: -1
            };
        },
        methods: {
            //修改
            editProduct() {
                editProduct(this.productForm).then(result => {
                    this.queryData();
                    this.editVisible = false;
                    this.$message.success(result.data.msg);
                })
            },
            //新增
            saveEdit() {
                if (this.productForm.parentId == 0) {
                    this.productForm.level = 0
                } else {
                    this.productForm.level = 1
                }
                addProduct(this.productForm).then(result => {
                    if (result.data.code == 200) {
                        this.$message.success(result.data.msg);
                        this.editVisible = false;
                        this.productForm = {
                            name: '',
                            parentId: 0,
                            productUnit: '',
                            sort: 0,
                            navStatus: 1,
                            showStatus: 1,
                            icon: '',
                            keywords: '',
                            description: ''
                        };
                        this.queryData();
                    } else {
                        this.$message.error(result.data.msg);
                    }
                })
            },
            //每页条数数改变
            handleSizeChange(val) {
                this.query.size = val;
                this.query.current = 1;
                this.queryData();
            },
            //当前页改变
            handlePageChange(val) {
                this.query.current = val;
                this.queryData();
            },
            //查询下拉框
            queryOptions() {
                queryProductOptions().then(result => {
                    this.options = result.data.data;
                })
            },
            //表格展示
            queryData() {
                productTable(this.query).then(result => {
                    this.tableData = result.data.data.records;
                    this.pageTotal = result.data.data.total;
                })
            },
            //查看下级
            checkSubordinate(index, row) {
                this.$set(this.query, "id", row.id);
                this.$set(this.query, "level", this.query.level + 1);
                this.queryData();
                this.query.id = null;
            },
            //返回上级
            upSubordinate() {
                this.$set(this.query, "level", this.query.level - 1);
                this.$set(this.query, "current", 1);
                this.queryData();
            },
            //是否显示在导航栏
            switchChange(row) {
                updateNavstatus({id: row.id, navStatus: row.navStatus}).then(result => {
                    this.$message.success("修改成功");
                })
            },
            //显示状态
            statusChange(row) {
                updateStatus({id: row.id, showStatus: row.showStatus}).then(result => {
                    this.$message.success("修改成功");
                })
            },
            //新增弹框
            addProduct() {
                this.queryOptions();
                this.editVisible = true;
            },
            //修改弹框
            editPro(index, row) {
                queryProduct({id: row.id}).then(result => {
                    this.productForm = result.data.data;
                    this.editVisible = true;
                })
            },
            //弹框关闭事件
            close() {
                this.productForm = {
                    id: '',
                    name: '',
                    parentId: 0,
                    productUnit: '',
                    sort: 0,
                    navStatus: 1,
                    showStatus: 1,
                    icon: '',
                    keywords: '',
                    description: ''
                };
            }
        },
        mounted() {
            this.queryData();
        },
        //离开当前路由事件
        beforeRouteLeave(to, form, next) {
            this.query.level = 0;
            this.query.id = 0;
            this.queryData();
            next();
        },
    }
    ;
</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }

    .table {
        width: 100%;
        font-size: 14px;
    }

    .red {
        color: #ff0000;
    }

    .mr10 {
        margin-right: 10px;
    }

    .table-td-thumb {
        display: block;
        margin: auto;
        width: 40px;
        height: 40px;
    }
</style>
