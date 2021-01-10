<template>
    <div>
        <product-detail :is-edit="false"></product-detail>
    </div>
</template>

<script>
    import ProductDetail from './ProductDetail.vue'
    export default {
        name: "ProductAdd",
        components:{ProductDetail}
    }
</script>

<style scoped>

</style>