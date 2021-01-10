<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-tagfill"></i> 商品类型
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
                >新增数据
                </el-button>
                <el-input v-model="queryCategory.name" placeholder="属性名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="queryAttributeData">搜索</el-button>
                <el-button type="info" plain icon="el-icon-loading" @click="resetAttributeData">重置表格</el-button>
            </div>
            <el-table
                    :data="tableData"
                    border
                    class="table"
                    header-cell-class-name="table-header"
            >
                <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="商品名称"></el-table-column>
                <el-table-column prop="attributeCount" label="属性数量"></el-table-column>
                <el-table-column prop="paramCount" label="参数数量"></el-table-column>

                <el-table-column label="设置" align="center">
                    <template slot-scope="scope">
                        <el-button
                                size="small"
                                icon="el-icon-edit"
                                @click="attributeList(scope.row)"
                        >属性列表
                        </el-button>
                        <el-button
                                size="small"
                                icon="el-icon-edit"
                                @click="parameterList(scope.row)"
                        >参数列表
                        </el-button>
                    </template>
                </el-table-column>

                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="primary"
                                size="small"
                                icon="el-icon-edit"
                                @click="editAttribute(scope.row)"
                        >编 辑
                        </el-button>
                        <el-button
                                type="danger"
                                size="small"
                                icon="el-icon-edit"
                                @click="deleteAttribute(scope.row)"
                        >删 除
                        </el-button>
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
        <!-- 新增修改弹出框 -->
        <el-dialog :title="titleName" :visible.sync="editVisible" width="40%" @close='close'>
            <el-form :model="attributeForm" label-width="70px">
                <el-form-item label="分类名称">
                    <el-input v-model="attributeForm.name"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAttribute">提 交</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    import {fetchCategoryData, saveOrUpdateAttributeData, deleteCategory} from "../../../api/category";

    export default {
        name: "ProductAttribute",
        data() {
            return {
                queryCategory: {
                    name: '',
                    current: 1,
                    size: 5,
                },
                attributeForm: {
                    id: '',
                    pname: '',
                    attributeCount: 0,
                    paramCount: 0
                },
                titleName: '',
                tableData: [],
                pagesizes: [5, 10, 15, 20],
                pageTotal: 0,
                editVisible: false
            }
        },

        methods: {
            //属性列表
            attributeList(row) {
                this.attributeForm = {
                    id: row.id,
                    pname: row.name,
                    attributeCount: 0,
                    paramCount: 0
                };
                this.$router.push({
                    path: "/AttributeList",
                    query: {pid: this.attributeForm.id, pname: this.attributeForm.pname}
                })
            },
            //重置表格
            resetAttributeData() {
                this.queryCategory = {
                    name: '',
                    current: 1,
                    size: 5
                };
                this.queryAttributeData();
            },
            //删除
            deleteAttribute(row) {
                this.$confirm('这会永久删除掉这条数据及其所属的所有参数与属性    !   !   !   确定嘛?', '提示', {
                    confirmButtonText: '没错',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    deleteCategory({id: row.id}).then(result => {
                        this.queryCategory.current = 1;
                        this.queryAttributeData();
                        this.$message({
                            type: 'success',
                            message: result.data.msg
                        });
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            },
            //弹框关闭事件
            close() {
                this.attributeForm = {
                    name: ""
                }
            },
            //新增或修改
            saveAttribute() {
                saveOrUpdateAttributeData(this.attributeForm).then(result => {
                    this.$message.success(result.data.msg);
                    this.editVisible = false;
                    this.queryAttributeData();
                })
            },
            //弹框
            editAttribute(row) {
                this.editVisible = true;

                if (row.id != null) {
                    this.attributeForm.id = row.id;
                    this.attributeForm.name = row.name;
                    this.titleName = "修改分类名称"
                }
                this.titleName = "新增分类"
            },
            //每页条数数改变
            handleSizeChange(val) {
                this.queryCategory.size = val;
                this.queryCategory.current = 1;
                this.queryAttributeData();
            },
            //当前页改变
            handlePageChange(val) {
                this.queryCategory.current = val;
                this.queryAttributeData();
            },

            queryAttributeData() {
                fetchCategoryData(this.queryCategory).then(result => {
                    this.tableData = result.data.data.records;
                    this.pageTotal = result.data.data.total;
                })
            },
        },

        mounted() {
            this.queryAttributeData()
        }
    }
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