<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-user"></i> 角色列表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                        type="primary"
                        icon="el-icon-delete"
                        class="handle-del mr10"
                        @click="editRole"
                >新增数据
                </el-button>
                <el-input v-model="query.name" placeholder="角色名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="queryRole">搜索</el-button>
                <el-button type="info" plain icon="el-icon-loading" @click="resetData">重置表格</el-button>
            </div>
            <el-table
                    :data="tableData"
                    border
                    class="table"
                    header-cell-class-name="table-header"
            >
                <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="角色名称"></el-table-column>
                <el-table-column prop="description" label="描述"></el-table-column>
                <el-table-column prop="adminCount" label="用户数">
                </el-table-column>
                <el-table-column prop="createTime" label="添加时间"></el-table-column>
                <el-table-column prop="status" label="是否启用">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.status"
                                   :active-value="1"
                                   inactive-value="0"
                                   @change="switchStatus(scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="240px">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-lx-friendaddfill"
                                @click="editMenuDialog(scope.row)"
                        >分配菜单
                        </el-button>
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-lx-friendaddfill"
                                @click="editResourceDialog(scope.row)"
                        >分配资源
                        </el-button>
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-edit"
                                @click="editRole(scope.row)"
                        >编 辑
                        </el-button>
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-delete"
                                @click="deleteRole(scope.row)"
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
        <el-dialog :title="titleName" :visible.sync="editVisible" width="40%" @close='close'>
            <el-form :model="roleForm" label-width="160px">
                <el-form-item label="账号">
                    <el-input v-model="roleForm.name" style="width: 230px;"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input
                            :autoSize="true"
                            v-model="roleForm.description"
                            style="width: 300px"
                            type="textarea"
                            placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="是否启用">
                    <el-radio-group v-model="roleForm.status">
                        <el-radio :label="1">启用</el-radio>
                        <el-radio :label="0">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAdmin">提 交</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    import {queryRole, updateRoleStatus, saveOrUpdateRole, getRoleById, deleteRole} from "../../../api/ums/role";

    export default {
        name: "Role",
        data() {
            return {
                query: {
                    name: '',
                    current: 1,
                    size: 5,
                },
                tableData: [],
                pagesizes: [5, 10, 15, 20],
                pageTotal: 0,
                editVisible: false,
                roleForm: {
                    id: '',
                    name: '',
                    status: 1,
                    description: ''
                },
                titleName: "新增角色"
            }
        },

        methods: {
            /*提交新增或修改*/
            saveAdmin() {
                saveOrUpdateRole(this.roleForm).then(result => {
                    let data = result.data;
                    if (data.code === 200) {
                        this.$message.success(data.msg);
                        this.editVisible = false;
                        this.queryRole();
                    }
                })
            },
            /*弹框关闭*/
            close() {
                this.roleForm = {
                    id: '',
                    name: '',
                    status: 1,
                    description: ''
                }
            },

            /*分配菜单*/
            editMenuDialog(row) {
                this.$router.push({
                    path:"/menuTree",
                    query:{
                        id:row.id
                    }
                })

            },
            /*资源*/
            editResourceDialog(row) {
                this.$router.push({
                    path:"/resourceCategoryTree",
                    query:{
                        id:row.id
                    }
                });
            },
            /*重置*/
            resetData() {
                this.query = {
                    name: '',
                    current: 1,
                    size: 5,
                },
                    this.queryRole();
            },
            /*启用状态*/
            switchStatus(row) {
                updateRoleStatus({id: row.id, status: row.status}).then(result => {
                    this.$message.success("修改成功");
                })
            },
            /*编辑弹框*/
            editRole(row) {
                if (row.id != null) {
                    this.titleName = "编辑角色";
                    getRoleById(row.id).then(result => {
                        this.roleForm = result.data.data;
                    })
                } else {
                    this.titleName = "编辑角色";
                }
                this.editVisible = true;
            },
            /*删除弹框*/
            deleteRole(row) {
                this.$confirm('这会永久删除掉这个角色    !   !   !   确定嘛?', '提示', {
                    confirmButtonText: '没错',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    deleteRole(row.id).then(result => {
                        this.query.current = 1;
                        this.queryRole();
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
                this.queryRole();
            },
            //当前页改变
            handlePageChange(val) {
                this.query.current = val;
                this.queryRole();
            },
            queryRole() {
                queryRole(this.query).then(result => {
                    this.tableData = result.data.data.records;
                    this.pageTotal = result.data.data.total
                })
            }

        },

        mounted() {
            this.queryRole();
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