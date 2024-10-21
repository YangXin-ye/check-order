<template>
  <div id="app">
    <layout />
  </div>
</template>
<script>
import layout from '@/components/layout/index.vue'
export default {
  components: {
    layout
  },
  mounted() {
    document.querySelector('#all_loading').style.display = 'none'
  },
  methods: {}
}
</script>
<style lang="less">
#app {
  width: 100%;
  overflow-x: hidden;
  background: linear-gradient(to right, #2c324d, #3f4561);
  background-size: cover;
}
</style>
