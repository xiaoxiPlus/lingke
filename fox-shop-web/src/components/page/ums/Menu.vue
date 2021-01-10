<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-s-unfold"></i> 菜单列表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                        type="primary"
                        icon="el-icon-box"
                        class="handle-del mr10"
                        @click="editMenu"
                >新增数据
                </el-button>
                <span>{{levelName}}</span>
            </div>
            <el-table
                    :data="tableData"
                    border
                    class="table"
                    header-cell-class-name="table-header"
            >
                <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
                <el-table-column prop="title" label="菜单名称"></el-table-column>
                <el-table-column prop="level" label="菜单级数"></el-table-column>
                <el-table-column prop="name" label="前端名称"></el-table-column>
                <el-table-column prop="icon" label="前端图标" align="center">
                    <template slot-scope="scope">
                        <span :class="scope.row.icon"></span>
                    </template>
                </el-table-column>
                <el-table-column prop="hidden" label="是否启用">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.hidden"
                                   :active-value="0"
                                   inactive-value="1"
                                   @change="switchFactoryStatus(scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="设置" align="center" width="240px">
                    <template slot-scope="scope">
                        <el-button
                                v-if="scope.row.level == 0"
                                type="primary"
                                size="small"
                                @click="queryLevelMenu(scope.row)"
                        >查看下级
                        </el-button>
                        <el-button
                                v-else
                                type="default"
                                size="small"
                                @click="queryMenu(scope.row)"
                        >查看上级
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="240px">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-edit"
                                @click="editMenu(scope.row)"
                        >编 辑
                        </el-button>
                        <el-button
                                type="text"
                                size="small"
                                icon="el-icon-delete"
                                @click="deleteMenu(scope.row)"
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
            <el-form :model="menuForm" label-width="160px">
                <el-form-item label="菜单名称">
                    <el-input v-model="menuForm.title" style="width: 290px;"></el-input>
                </el-form-item>
                <el-form-item label="选择上级菜单">
                    <el-select v-model="menuForm.parentId">
                        <el-option :value="0" label="无上级菜单"></el-option>
                        <el-option
                                v-for="item in categoryOptions"
                                :key="item.id"
                                :label="item.title"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="前端名称">
                    <el-input v-model="menuForm.name" style="width: 290px;"></el-input>
                </el-form-item>
                <el-form-item label="前端图标">
                    <el-input v-model="menuForm.icon" style="width: 240px;"></el-input>
                    <span :class="menuForm.icon"></span>
                </el-form-item>

                <el-form-item label="是否显示">
                    <el-radio-group v-model="menuForm.hidden">
                        <el-radio :label="1">隐藏</el-radio>
                        <el-radio :label="0">显示</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="menuForm.sort" style="width: 290px;"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdateMenu">提 交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {
        queryMenu,
        updateMenuStatus,
        saveOrUpdateMenu,
        getMenuById,
        deleteMenu,
        queryLevelMenu
    } from "../../../api/ums/menu";

    export default {
        name: "Menu",
        data() {
            return {
                query: {
                    current: 1,
                    size: 5,
                },
                tableData: [],
                categoryOptions: [],
                pagesizes: [5, 10, 15, 20],
                pageTotal: 0,
                editVisible: false,
                levelName: '',
                menuForm: {
                    id: '',
                    title: '',
                    parentId: 0,
                    name: '',
                    icon: '',
                    /*0 是展示 1是隐藏*/
                    hidden: 0,
                    sort: 0
                },
                titleName: "新增菜单",
            }
        },

        methods: {
            /*跳转下级*/
            queryLevelMenu(row) {
                this.levelName = row.title;
                queryLevelMenu({id: row.id}).then(result => {
                    this.tableData = result.data.data.records;
                    this.pageTotal = result.data.data.total;
                })
            },
            /*提交新增或修改*/
            saveOrUpdateMenu() {
                saveOrUpdateMenu(this.menuForm).then(result => {
                    let data = result.data;
                    if (data.code === 200) {
                        this.$message.success(data.msg);
                        this.editVisible = false;
                        this.queryMenu();
                    }
                })
            },
            /*弹框关闭*/
            close() {
                this.menuForm = {
                    id: '',
                    title: '',
                    parentId: 0,
                    name: '',
                    icon: '',
                    hidden: 0,
                    sort: 0
                }
            },

            /*启用状态*/
            switchFactoryStatus(row) {
                updateMenuStatus({id: row.id, hidden: row.hidden}).then(result => {
                    this.$message.success("修改成功");
                })
            },
            /*编辑弹框*/
            editMenu(row) {
                if (row.id != null) {
                    this.titleName = "修改菜单";
                    getMenuById(row.id).then(result => {
                        this.menuForm = result.data.data;
                    })
                }
                this.titleName = "新增菜单";
                this.editVisible = true;
            },
            /*删除弹框*/
            deleteMenu(row) {
                this.$confirm('这会永久删除掉这个菜单    !   !   !   确定嘛?', '提示', {
                    confirmButtonText: '没错',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    deleteMenu(row.id).then(result => {
                        this.query.current = 1;
                        this.queryMenu();
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
                this.queryMenu();
            },
            //当前页改变
            handlePageChange(val) {
                this.query.current = val;
                this.queryMenu();
            },

            queryMenu() {
                queryMenu(this.query).then(result => {
                    this.tableData = result.data.data.records;
                    this.pageTotal = result.data.data.total;
                    this.categoryOptions = result.data.data.records;
                    this.levelName = "";
                })
            },

        },

        mounted() {
            this.queryMenu();
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