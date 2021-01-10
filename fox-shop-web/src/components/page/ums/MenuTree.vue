<template>
    <div>
        <el-tree
                :data="data"
                show-checkbox
                node-key="id"
                default-expand-all
                ref="tree"
                :props="defaultProps">
        </el-tree>
        <center>
            <el-button type="primary" @click="saveOrUpdate">提交</el-button>

            <el-button type="danger" @click="resetTree">返回上一级</el-button>
        </center>
    </div>
</template>

<script>
    import {queryMenuTree, queryMenuChecked, saveOrUpdateMenuTree} from "../../../api/ums/menu";

    export default {
        name: "MenuTree",
        data() {
            return {
                data: [],
                defaultProps: {
                    children: 'children',
                    label: 'label'
                },
                parentId: this.$route.query.id,
            };
        },
        watch: {
            '$route'(to, from) {
                this.$router.go(0);
            },
        },
        methods: {
            resetTree() {
                this.$router.push("/role")
            },

            queryMenuTree() {
                queryMenuTree().then(result => {
                    this.data = result.data;
                    queryMenuChecked(this.parentId).then(result => {
                        let data = result.data.data;
                        let that = this;
                        setTimeout(function () {
                            data.forEach(value => {
                                that.$refs.tree.setChecked(value, true, false);
                            });
                        }, 500);
                    })
                })
            },
            saveOrUpdate() {
                /*获取所有被选中的节点*/
                let menuIds = this.$refs.tree.getCheckedKeys();
                saveOrUpdateMenuTree({parentId: this.parentId, menuIds: menuIds}).then(result => {
                    this.$router.push("/role")
                    this.$message.success("赋予权限成功")
                })

            }
        },

        mounted() {
            this.queryMenuTree();
        }
    }
</script>

<style scoped>

</style>