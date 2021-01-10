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

                <el-input v-model="query.name" placeholder="品牌名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="queryData">搜索</el-button>
                <el-button type="info" plain style="float: right" size="medium" icon="el-icon-lx-add"
                           @click="editBrand">添加品牌
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
                <el-table-column prop="name" label="品牌名称"></el-table-column>
                <el-table-column prop="firstLetter" label="品牌首字母"></el-table-column>
                <el-table-column prop="sort" label="排序"></el-table-column>
                <el-table-column prop="factoryStatus" label="品牌制造商">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.factoryStatus"
                                   :active-value="1"
                                   inactive-value="0"
                                   @change="switchFactoryStatus(scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column prop="showStatus" label="是否显示">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.showStatus"
                                   :active-value="1"
                                   inactive-value="0"
                                   @change="switchFactoryStatus(scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column prop="sort" label="相关">
                    <template slot-scope="scope">
                        产品数量：{{scope.row.productCount}}<br>评价数量：{{scope.row.productCommentCount}}
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="230" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="primary"
                                size="small"
                                icon="el-icon-edit"
                                @click="editBrand(scope.$index, scope.row)"
                        >编 辑
                        </el-button>
                        <el-button
                                type="danger"
                                size="small"
                                icon="el-icon-edit"
                                @click="deleteBrand(scope.$index, scope.row)"
                        >删 除
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
        <el-dialog title="品牌分类维护" :visible.sync="editVisible" width="45%" @close='close'>
            <el-form ref="form" :model="brandForm" label-width="130px">
                <el-form-item label="*品牌名称">
                    <el-input v-model="brandForm.name"></el-input>
                </el-form-item>
                <el-form-item label="品牌首字母">
                    <el-input v-model="brandForm.firstLetter"></el-input>
                </el-form-item>

                <el-form-item label="*品牌LOGO">
                    <single-upload-file v-model="brandForm.logo"
                    ></single-upload-file>
                </el-form-item>
                <el-form-item label="品牌专区大图">
                    <single-upload-file v-model="brandForm.bigPic"
                    ></single-upload-file>
                </el-form-item>

                <el-form-item label="品牌故事">
                    <el-input v-model="brandForm.brandStory"></el-input>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="brandForm.sort"></el-input>
                </el-form-item>
                <el-form-item label="是否显示">
                    <el-switch v-model="brandForm.showStatus"
                               :active-value="1"
                               inactive-value="0"
                    ></el-switch>
                </el-form-item>
                <el-form-item label="品牌制造商">
                    <el-switch v-model="brandForm.factoryStatus"
                               :active-value="1"
                               inactive-value="0"
                    ></el-switch>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button v-if="brandForm.id == '' " type="primary" @click="saveBrand">确 定</el-button>
                <el-button v-else type="primary" @click="saveBrand">修 改</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {
        queryBrandData,
        saveOrUpdateBrand,
        queryBrandById,
        deleteBrand,
        updateFactoryStatus
    } from "../../../api/brand";
    import singleUploadFile from '../../brandSingleUpload/brandSingleUploadFile';

    export default {
        name: "ProductBrand",
        components: {singleUploadFile},
        data() {
            return {
                query: {
                    name: '',
                    current: 1,
                    size: 10,
                    level: 0,
                },
                editVisible: false,
                pagesizes: [5, 10, 15, 20],
                tableData: [],
                pageTotal: 0,
                brandForm: {
                    id: '',
                    name: '',
                    firstLetter: '',
                    brandStory: '',
                    sort: 0,
                    showStatus: 1,
                    factoryStatus: 1,
                    logo: '',
                    bigPic: ''
                },
            }
        },

        methods: {
            close() {
                this.brandForm = {
                    id: '',
                    name: '',
                    firstLetter: '',
                    brandStory: '',
                    sort: 0,
                    showStatus: 1,
                    factoryStatus: 1,
                    logo: '',
                    bigPic: ''
                }
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
            //查询所有数据
            queryData() {
                queryBrandData(this.query).then(result => {
                    this.tableData = result.data.data.records;
                    this.pageTotal = result.data.data.total
                })
            },
            /*编辑*/
            editBrand(index, row) {
                this.editVisible = true;
                if (row.id != null) {
                    queryBrandById(row.id).then(result => {
                        this.brandForm = result.data.data;
                    })
                }
            },
            /*新增 修改*/
            saveBrand() {
                saveOrUpdateBrand(this.brandForm).then(result => {
                    this.editVisible = false;
                    this.$message.success(result.data.msg);
                    this.queryData();
                })
            },
            /*删除*/
            deleteBrand(index, row) {
                this.$confirm('这会永久删除掉这个品牌    !   !   !   确定嘛?', '提示', {
                    confirmButtonText: '没错',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    deleteBrand(row.id).then(result => {
                        this.$message.success("删除成功");
                        this.query.current = 1;
                        this.queryData();
                    })
                })


            },
            /*品牌制造商*/
            switchFactoryStatus(row) {
                updateFactoryStatus({
                    id: row.id,
                    factoryStatus: row.factoryStatus,
                    showStatus: row.showStatus
                }).then(result => {
                    this.$message.success("修改成功")
                })
            },
        },

        mounted() {
            this.queryData();
        }
    }
</script>

<style scoped>

</style>