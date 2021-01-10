<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-user-solid"></i> 用户列表
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
                <el-input v-model="query.username" placeholder="属性名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="queryAdmin">搜索</el-button>
                <el-button type="info" plain icon="el-icon-loading" @click="resetData">重置表格</el-button>
            </div>
            <el-table
                    :data="tableData"
                    border
                    class="table"
                    header-cell-class-name="table-header"
            >
                <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
                <el-table-column prop="username" label="账号"></el-table-column>
                <el-table-column prop="nickName" label="昵称"></el-table-column>
                <el-table-column prop="email" label="邮箱"></el-table-column>
                <el-table-column prop="createTime" label="添加时间"></el-table-column>
                <el-table-column prop="loginTime" label="最后登陆时间"></el-table-column>
                <el-table-column prop="status" label="是否启用">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.status"
                                   :active-value="1"
                                   inactive-value="0"
                                   @change="switchFactoryStatus(scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="240px">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-lx-friendaddfill"
                                @click="allocationRole(scope.row)"
                        >分配角色
                        </el-button>
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-edit"
                                @click="editAttribute(scope.row)"
                        >编 辑
                        </el-button>
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-delete"
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
            <el-form :model="adminForm" label-width="160px">
                <el-form-item label="账号">
                    <el-input v-model="adminForm.username" style="width: 230px;"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                    <el-input v-model="adminForm.nickName" style="width: 230px;"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="adminForm.email" style="width: 230px;"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input
                            :autoSize="true"
                            v-model="adminForm.note"
                            style="width: 300px"
                            type="textarea"
                            placeholder="请输入内容"></el-input>
                </el-form-item>
                <el-form-item label="是否启用">
                    <el-radio-group v-model="adminForm.status">
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


        <el-dialog title="分配角色" :visible.sync="editRole" width="30%" @close='close'>
            <el-form label-width="90px">
                <el-form-item label="账号">
                    <el-select v-model="roleId" multiple placeholder="请选择" style="width: 300px">
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdateRoleRelation">分 配</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    import {queryAdmin, updateAdminStatus, saveOrUpdateAdmin, getAdminById, deleteAdmin} from "../../../api/ums/admin";
    import {queryAdminByOptions, getRoleByAdminId, saveOrUpdateRoleRelation} from "../../../api/ums/role";

    export default {
        name: "admin",
        data() {
            return {
                query: {
                    username: '',
                    current: 1,
                    size: 5,
                },
                tableData: [],
                pagesizes: [5, 10, 15, 20],
                pageTotal: 0,
                editVisible: false,
                titleName: '编辑用户',
                editRole: false,
                adminForm: {
                    id: "",
                    username: '',
                    nickName: '',
                    email: '',
                    status: 1,
                    note: ''
                },
                roleId: [],
                options: [],
                adminId:''
            }
        },

        methods: {
            //新增或修改用户的角色
            saveOrUpdateRoleRelation() {
                alert(this.adminId);
                alert(JSON.stringify(this.roleId));
                saveOrUpdateRoleRelation({parentId:this.adminId,menuIds:this.roleId}).then(res => {
                    this.editRole = false;
                    this.$message({
                        message: '赋角色成功',
                        type: 'premary',
                        duration: 1000
                    });
                    this.queryAdmin();
                })
            },
            /*提交新增或修改*/
            saveAdmin() {
                saveOrUpdateAdmin(this.adminForm).then(result => {
                    let data = result.data;
                    if (data.code === 200) {
                        this.$message.success(data.msg);
                        this.editVisible = false;
                        this.queryAdmin();
                    }
                })
            },
            /*弹框关闭*/
            close() {
                this.adminForm = {
                    id: "",
                    username: '',
                    nickName: '',
                    email: '',
                    status: 1,
                    note: ''
                };
                this.roleId = [];
                this.admingId = '';
            },

            /*分配角色*/
            allocationRole(row) {
                queryAdminByOptions().then(res => {
                    let data = res.data.data;
                    this.options = data;
                });
                this.editRole = true;
                if (row.id != null) {
                    this.adminId = row.id;
                    getRoleByAdminId(row.id).then(res => {
                        let data = res.data.data;
                        for (let i = 0; i < data.length; i++) {
                            this.roleId.push(data[i].roleId);
                        }
                    })
                }

            },
            /*重置*/
            resetData() {
                this.query = {
                    username: '',
                    current: 1,
                    size: 5,
                },
                    this.queryAdmin();
            },
            /*启用状态*/
            switchFactoryStatus(row) {
                updateAdminStatus({id: row.id, status: row.status}).then(result => {
                    this.$message.success("修改成功");
                })
            },
            /*编辑弹框*/
            editAttribute(row) {
                if (row.id != null) {
                    getAdminById(row.id).then(result => {
                        this.adminForm = result.data.data;
                    })
                }
                this.editVisible = true;
            },
            /*删除弹框*/
            deleteAttribute(row) {
                this.$confirm('这会永久删除掉这个用户    !   !   !   确定嘛?', '提示', {
                    confirmButtonText: '没错',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    deleteAdmin(row.id).then(result => {
                        this.query.current = 1;
                        this.queryAdmin();
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
                this.queryAdmin();
            },
            //当前页改变
            handlePageChange(val) {
                this.query.current = val;
                this.queryAdmin();
            },

            queryAdmin() {
                queryAdmin(this.query).then(result => {
                    this.tableData = result.data.data.records;
                    this.pageTotal = result.data.data.total
                })
            }
        },

        mounted() {
            this.queryAdmin()
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