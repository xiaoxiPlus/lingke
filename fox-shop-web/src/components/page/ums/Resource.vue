<template>
    <div>
        <el-collapse>
            <el-collapse-item align="center">
                <template slot="title">
                    <font style="size: 13px;color: lightcoral">点此展开或收回</font>
                    <h1 style="margin-left: 550px;color: skyblue">条件查询</h1>
                </template>
                <br>
                资源名称：
                <el-input v-model="query.name" style="width: 290px;"></el-input>

                资源路径：
                <el-input v-model="query.url" style="width: 290px;"></el-input>

                资源分类：
                <el-select v-model="query.categoryId">
                    <el-option
                            v-for="item in categoryOptions"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
                <br>
                <br>
                查询创建时间的区间：
                <el-date-picker value-format="timestamp"
                                type="datetime" v-model="query.startTime" style="width: 290px;"></el-date-picker>
                --------------
                <el-date-picker value-format="timestamp"
                                type="datetime" v-model="query.endTime" style="width: 290px;"></el-date-picker>
                <br>
                <br>
                <el-button type="default" @click="resetData">重置</el-button>
                <el-button type="primary" @click="queryResource">查询</el-button>
            </el-collapse-item>

            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>
                        <i class="el-icon-s-check"></i> 资源列表
                    </el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="container">
                <div class="handle-box">
                    <el-button
                            type="primary"
                            icon="el-icon-box"
                            class="handle-del mr10"
                            @click="editResource"
                    >新增数据
                    </el-button>
                    <el-button
                            type="success"
                            icon="el-icon-box"
                            class="handle-del mr10"
                            @click="resourceCategory"
                    >资源分类
                    </el-button>
                </div>
                <el-table
                        :data="tableData"
                        border
                        class="table"
                        header-cell-class-name="table-header"
                >
                    <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
                    <el-table-column prop="name" label="资源名称"></el-table-column>
                    <el-table-column prop="url" label="资源路径"></el-table-column>
                    <el-table-column prop="description" label="描述"></el-table-column>
                    <el-table-column prop="createTime" label="创建时间" align="center">
                    </el-table-column>
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
                <el-form :model="resourceForm" label-width="160px">
                    <el-form-item label="资源名称">
                        <el-input v-model="resourceForm.name" style="width: 290px;"></el-input>
                    </el-form-item>
                    <el-form-item label="资源路径">
                        <el-input v-model="resourceForm.url" style="width: 290px;"></el-input>
                    </el-form-item>
                    <el-form-item label="资源分类">
                        <el-select v-model="resourceForm.categoryId">
                            <el-option
                                    v-for="item in categoryOptions"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input
                                :autoSize="true"
                                v-model="resourceForm.description"
                                style="width: 300px"
                                type="textarea"
                                placeholder="请输入内容"></el-input>
                    </el-form-item>

                </el-form>
                <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdateResource">提 交</el-button>
            </span>
            </el-dialog>
        </el-collapse>
    </div>
</template>

<script>
    import {
        queryResource,
        saveOrUpdateResource,
        getResourceById,
        deleteResource,
        queryResourceCategory
    } from "../../../api/ums/resource";

    export default {
        name: "Resource",
        data() {
            return {
                query: {
                    name: '',
                    url: '',
                    categoryId: null,
                    startTime: '',
                    endTime: '',
                    current: 1,
                    size: 5,
                },
                tableData: [],
                pagesizes: [5, 10, 15, 20],
                pageTotal: 0,
                categoryOptions: [],
                resourceForm: {
                    id: '',
                    name: '',
                    url: '',
                    categoryId: 1,
                    description: ''
                },
                editVisible: false,
            }
        },

        methods: {
            /*资源分类表*/
            resourceCategory(){
                this.$router.push("/resourceCategory");
            },
            /*提交新增或修改*/
            saveOrUpdateResource() {
                saveOrUpdateResource(this.resourceForm).then(result => {
                    let data = result.data;
                    if (data.code === 200) {
                        this.$message.success(data.msg);
                        this.editVisible = false;
                        this.queryResource();
                    }
                })
            },
            /*弹框关闭*/
            close() {
                this.resourceForm = {
                    id: '',
                    name: '',
                    url: '',
                    categoryId: 1,
                    description: ''
                }
            },
            /*重置*/
            resetData() {
                this.query = {
                    name: '',
                    url: '',
                    categoryId: null,
                    startTime: '',
                    endTime: '',
                    current: 1,
                    size: 5,
                };
                this.queryResource();
            },
            /*编辑弹框*/
            editResource(row) {

                if (row.id != null) {
                    getResourceById(row.id).then(result => {
                        this.resourceForm = result.data.data;
                    })
                }
                this.editVisible = true;
            },
            /*删除弹框*/
            deleteResource(row) {
                this.$confirm('这会永久删除掉这个资源    !   !   !   确定嘛?', '提示', {
                    confirmButtonText: '是的',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    deleteResource(row.id).then(result => {
                        this.query.current = 1;
                        this.queryResource();
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
            //每页条数数改变
            handleSizeChange(val) {
                this.query.size = val;
                this.query.current = 1;
                this.queryResource();
            },
            //当前页改变
            handlePageChange(val) {
                this.query.current = val;
                this.queryResource();
            },

            queryResource() {
                queryResource(this.query).then(result => {
                    this.tableData = result.data.data.records;
                    this.pageTotal = result.data.data.total
                })
            },

            queryCategoryOptions() {
                queryResourceCategory().then(result => {
                    this.categoryOptions = result.data.data;
                });
            }
        },

        mounted() {
            this.queryResource();
            this.queryCategoryOptions();
        }
    }
</script>

<style scoped>

</style>