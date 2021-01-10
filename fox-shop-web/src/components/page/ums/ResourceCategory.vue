<template>
    <div>

        <div class="container">
            <div class="handle-box">
                <el-button
                        type="primary"
                        icon="el-icon-box"
                        class="handle-del mr10"
                        @click="editResource"
                        style="float: right" plain
                >新增分类
                </el-button>
            </div>
            <el-table
                    :data="tableData"
                    border
                    class="table"
                    header-cell-class-name="table-header"
            >
                <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="分类名称"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
                <el-table-column prop="sort" label="排序"></el-table-column>
                <el-table-column label="操作" align="center" width="240px">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-edit"
                                @click="editResource(scope.row)"
                        >编 辑
                        </el-button>
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-delete"
                                @click="deleteResource(scope.row)"
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
        <el-dialog title="编辑资源" :visible.sync="editVisible" width="40%" @close='close'>
            <el-form :model="categoryForm" label-width="160px">
                <el-form-item label="名称">
                    <el-input v-model="categoryForm.name" style="width: 290px;"></el-input>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="categoryForm.sort" style="width: 290px;"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="editResourceCategory">提 交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {
        queryResourceCategory,
        saveOrUpdateResourceCategory,
        getResourceCategoryById,
        deleteResource
    } from "../../../api/ums/resourceCategory";

    export default {
        name: "ResourceCategory",
        data() {
            return {
                query: {
                    current: 1,
                    size: 5,
                },
                tableData: [],
                pagesizes: [5, 10, 15, 20],
                pageTotal: 0,
                categoryForm: {
                    name: '',
                    sort: 0
                },
                editVisible: false,

            }
        },

        methods: {
            close() {

            },
            /*编辑弹框*/
            editResource(row) {
                if (row.id != null) {
                    getResourceCategoryById(row.id).then(result => {
                        this.categoryForm = result.data.data;
                    })
                }
                this.editVisible = true;
            },
            /*删除弹框*/
            deleteResource(row) {
                this.$confirm('这会永久删除掉这个资源分类   !   !   !   确定嘛?', '提示', {
                    confirmButtonText: '是的',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    deleteResource(row.id).then(result => {
                        this.query.current = 1;
                        this.queryResourceCategory();
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
            /*新增修改*/
            editResourceCategory() {
                saveOrUpdateResourceCategory(this.categoryForm).then(result => {
                    let data = result.data;
                    if (data.code === 200) {
                        this.$message.success(data.msg);
                        this.editVisible = false;
                        this.queryResourceCategory();
                    }
                })
            },
            //每页条数数改变
            handleSizeChange(val) {
                this.query.size = val;
                this.query.current = 1;
                this.queryResourceCategory();
            },
            //当前页改变
            handlePageChange(val) {
                this.query.current = val;
                this.queryResourceCategory();
            },
            /*查询*/
            queryResourceCategory() {
                queryResourceCategory(this.query).then(result => {
                    this.tableData = result.data.data.records;
                    this.pageTotal = result.data.data.total
                })
            }
        },

        mounted() {
            this.queryResourceCategory();
        }
    }
</script>

<style scoped>

</style>