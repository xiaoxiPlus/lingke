<template>
    <div>
        <el-card class="form-container" shadow="never">
            <div v-for="cate in categoryData">
                <el-row class="table-layout" style="background: #F2F6FC;">
                    <el-checkbox
                    @Change="parentCheckbox"
                    ></el-checkbox>
                    {{cate.name}}
                </el-row>
                <el-row class="table-layout">
                    <el-col v-for=" resourceAttr in getResourceByCategoryId(cate.id)" style="padding: 4px 0">
                        <el-checkbox
                            :value="resourceAttr.id"
                        ></el-checkbox>
                        {{resourceAttr.name}}
                    </el-col>
                </el-row>
            </div>
        </el-card>
    </div>
</template>

<script>
    import {queryResourceCategory, queryResourceData} from "../../../api/ums/resource";

    export default {
        name: "ResourceCategoryTree",
        data() {
            return {
                parentId: this.$route.query.id,
                categoryData: [],
                resourceData: []
            }
        },
        watch: {
            '$route'(to, from) {
                this.$router.go(0);
            },
        },
        methods: {
            //父级复选框
            parentCheckbox(){

            },

            queryCategory() {
                queryResourceCategory().then(result => {
                    this.categoryData = result.data.data;
                });
                queryResourceData().then(result => {
                    this.resourceData = result.data.data;
                })

            },

            getResourceByCategoryId(categoryId) {
                let resourceAttr = [];
                for (let i = 0; i < this.resourceData.length; i++) {
                    if (this.resourceData[i].categoryId === categoryId) {
                        resourceAttr.push(this.resourceData[i]);
                    }
                }
                return resourceAttr
            }
        },

        mounted() {
            this.queryCategory();
        }
    }
</script>

<style scoped>
    .table-layout {
        padding: 20px;
        border-left: 1px solid #DCDFE6;
        border-right: 1px solid #DCDFE6;
        border-bottom: 1px solid #DCDFE6;
    }

    .top-line {
        border-top: 1px solid #DCDFE6;
    }
</style>