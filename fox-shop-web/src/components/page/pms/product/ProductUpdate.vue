<template>
    <div>
        <product-detail :is-edit="true"></product-detail>
    </div>
</template>

<script>
    import ProductDetail from './ProductDetail.vue'

    export default {
        name: "ProductUpdate",
        components: {ProductDetail},
        data() {
            return {}
        },

        methods: {},
        mounted() {
        }
    }
</script>

<style scoped>

</style>