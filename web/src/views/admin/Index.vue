<template>
  <div class="page-header-index-wide">
    <a-card :bordered="false" :bodyStyle="{ padding: '16px 0', height: '100%' }" :style="{ height: '100%' }">
      <div class="admin-info-main" :class="{ 'mobile': isMobile }">
        <div class="admin-info-left">
          <a-menu
            :mode="isMobile ? 'horizontal' : 'inline'"
            :style="{ border: '0', width: isMobile ? '560px' : 'auto'}"
            :selectedKeys="selectedKeys"
            type="inner"
            @openChange="onOpenChange"
          >
            <a-menu-item key="/admin/user">
              <router-link :to="{ name: 'User' }">
                {{ $t('menu.admin.user') }}
              </router-link>
            </a-menu-item>
            <a-menu-item key="/admin/audit">
              <router-link :to="{ name: 'AdminAudit' }">
                {{ $t('menu.admin.audit') }}
              </router-link>
            </a-menu-item>
          </a-menu>
        </div>
        <div class="admin-info-right">
          <div class="admin-info-title">
            <span>{{ $t($route.meta.title) }}</span>
          </div>
          <route-view></route-view>
        </div>
      </div>
    </a-card>
  </div>
</template>

<script>
import { RouteView } from '@/layouts'
import { baseMixin } from '@/store/app-mixin'

export default {
  components: {
    RouteView
  },
  mixins: [baseMixin],
  data () {
    return {
      mode: 'inline',
      openKeys: [],
      selectedKeys: [],
      preview: {},
      pageTitle: ''
    }
  },
  mounted () {
    this.updateMenu()
  },
  methods: {
    onOpenChange (openKeys) {
      this.openKeys = openKeys
    },
    updateMenu () {
      const routes = this.$route.matched.concat()
      this.selectedKeys = [ routes.pop().path ]
    }
  },
  watch: {
    '$route' (val) {
      this.updateMenu()
    }
  }
}
</script>

<style lang="less" scoped>
  .admin-info-main {
    width: 100%;
    display: flex;
    height: 100%;
    overflow: auto;

    &.mobile {
      display: block;

      .admin-info-left {
        border-right: unset;
        border-bottom: 1px solid #e8e8e8;
        width: 100%;
        height: 50px;
        overflow-x: auto;
        overflow-y: scroll;
      }
      .admin-info-right {
        padding: 20px 40px;
      }
    }

    .admin-info-left {
      border-right: 1px solid #e8e8e8;
      width: 165px;
    }

    .admin-info-right {
      flex: 1 1;
      padding: 8px 40px;

      .admin-info-title {
        color: rgba(0,0,0,.85);
        font-size: 20px;
        font-weight: 500;
        line-height: 28px;
        margin-bottom: 12px;
      }
      .admin-info-view {
        padding-top: 12px;
      }
    }
  }

</style>
