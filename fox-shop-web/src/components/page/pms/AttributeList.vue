<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-tagfi  ll"></i> 属性列表
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
                <el-table-column prop="name" label="属性名称"></el-table-column>
                <el-table-column label="商品类型">{{parentForm.pname}}</el-table-column>
                <el-table-column label="属性是否可选">
                    <template slot-scope="scope">
                        <span v-if="scope.row.selectType == 0">唯一</span>
                        <span v-else-if="scope.row.selectType == 1">单选</span>
                        <span v-else>复选</span>
                    </template>
                </el-table-column>
                <el-table-column label="属性值的录入方式">
                    <template slot-scope="scope">
                        <span v-if="scope.row.inputType == 0">手工录入</span>
                        <span v-else>从列表中选取</span>
                    </template>
                </el-table-column>
                <el-table-column prop="inputList" label="可选值列表"></el-table-column>
                <el-table-column prop="sort" label="排序"></el-table-column>


                <el-table-column label="操作" align="center" width="200px">
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
        <el-dialog :title="titleName" :visible.sync="editVisible" width="50%" @close='close'>
            <el-form :model="attributeForm" label-width="160px">
                <el-form-item label="分类名称">
                    <el-input v-model="attributeForm.name" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item label="商品类型">
                    <el-input v-model="parentForm.pname" disabled style="width: 50%"></el-input>
                </el-form-item>
                <el-form-item label="分类筛选样式">
                    <el-radio-group v-model="attributeForm.filterType">
                        <el-radio :label="0">普通</el-radio>
                        <el-radio :label="1">颜色</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="能否进行检索">
                    <el-radio-group v-model="attributeForm.searchType">
                        <el-radio :label="0">不需要检索</el-radio>
                        <el-radio :label="1">关键字检索</el-radio>
                        <el-radio :label="2">范围检索</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="商品属性关联">
                    <el-radio-group v-model="attributeForm.relatedStatus">
                        <el-radio :label="0">否</el-radio>
                        <el-radio :label="1">是</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="属性是否可选">
                    <el-radio-group v-model="attributeForm.selectType">
                        <el-radio :label="0">唯一</el-radio>
                        <el-radio :label="1">单选</el-radio>
                        <el-radio :label="2">多选</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="属性录入方式">
                    <el-radio-group v-model="attributeForm.inputType">
                        <el-radio :label="0">手工录入</el-radio>
                        <el-radio :label="1">从下面列表中选择</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="属性值可选值列表">
                    <el-input type="textarea" rows="5" v-model="inputListFormat" style="width: 70%"></el-input>
                </el-form-item>
                <el-form-item label="是否支持手动新增">
                    <el-radio-group v-model="attributeForm.handAddStatus">
                        <el-radio :label="0">不支持</el-radio>
                        <el-radio :label="1">支持</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序属性">
                    <el-input v-model="attributeForm.sort" style="width: 70%"></el-input>
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
    import {attributeData, editAttributeData,deleteAttribute} from "../../../api/category";

    export default {
        name: "AttributeList",
        watch: {
            '$route'(to, from) {
                this.$router.go(0);
            },
            inputListFormat :function (newValue,oldValue) {
                newValue = newValue.replace(/\n/g,",");
                this.attributeForm.inputList=newValue;
            }

        },
        data() {
            return {
                queryCategory: {
                    name: '',
                    current: 1,
                    size: 5,
                    productAttributeCategoryId: this.$route.query.pid
                },
                titleName: '',
                parentForm: {
                    pname: this.$route.query.pname
                },
                attributeForm: {
                    id: '',
                    productAttributeCategoryId: this.$route.query.pid,
                    name: '',
                    filterType: 0,
                    searchType: 0,
                    relatedStatus: 0,
                    selectType: 0,
                    inputType: 0,
                    inputList: '',
                    handAddStatus: 0,
                    sort: 0,
                    type: 0
                },
                inputListFormat:'',
                editVisible: false,
                pagesizes: [5, 10, 15, 20],
                pageTotal: 0,
                tableData: []
            }
        },
        methods: {
            //删除
            deleteAttribute(row){
                this.$confirm('这会永久删除掉这条数据确定嘛?', '提示', {
                    confirmButtonText: '没错',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    deleteAttribute({id:row.id}).then(result=>{
                        this.queryCategory.current = 1;
                        this.queryAttributeData();
                        this.$message({
                            type: 'success',
                            message: result.data.msg
                        });

                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });


            },
            //新增修改
            saveAttribute() {
                editAttributeData(this.attributeForm).then(result => {
                    this.$message.success(result.data.msg);
                    this.editVisible = false;
                    this.queryAttributeData();
                })
            },
            //重置表单
            resetAttributeData() {
                this.queryCategory = {
                    name: '',
                    current: 1,
                    size: 5,
                    productAttributeCategoryId: this.$route.query.pid
                };
                this.inputListFormat='';
                this.queryAttributeData();
            },
            //新增修改的模板
            editAttribute(row) {
                this.editVisible = true;

                if (row.id != null) {
                    this.attributeForm = {
                        id: row.id,
                        productAttributeCategoryId: this.$route.query.pid,
                        name: row.name,
                        filterType: row.filterType,
                        searchType: row.searchType,
                        relatedStatus: row.relatedStatus,
                        selectType: row.selectType,
                        inputType: row.inputType,
                        inputList: row.inputList,
                        handAddStatus: row.handAddStatus,
                        sort: row.sort,
                        type: 0
                    };
                    this.inputListFormat = row.inputList.replaceAll("," , '\n');
                    this.titleName = "修改商品属性"
                }
                this.titleName = "添加商品属性";
                this.editVisible = true;
            },
            //查询数据
            queryAttributeData() {
                attributeData(this.queryCategory).then(result => {
                    this.tableData = result.data.data.records;
                    this.pageTotal = result.data.data.total;
                })
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
            close() {
                this.attributeForm = {
                    id: '',
                    productAttributeCategoryId: this.$route.query.pid,
                    name: '',
                    filterType: 0,
                    searchType: 0,
                    relatedStatus: 0,
                    selectType: 0,
                    inputType: 0,
                    inputList: '',
                    handAddStatus: 0,
                    sort: 0,
                    type: 0
                };
                this.inputListFormat='';
            }
        },

        mounted() {
            this.queryAttributeData();
        }
    };
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