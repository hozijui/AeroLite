<template>
  <div>
    <a-button type="primary" icon="user-add" @click="toggleCreateModal" @ok="confirmModify"
              style="float: right;margin-bottom: 16px;z-index: 20" v-action="'user:create'">
      {{ $t('admin.user.create') }}
    </a-button>

    <a-modal v-if="$auth('user:create')" :destroy-on-close="true" :visible="createModal"
             :title="$t('admin.user.create')" :okText="$t('admin.user.create.btn')"
             @cancel="toggleCreateModal" @ok="confirmCreate">
      <a-form :form="createForm" :label-col="{ span: 4 }" :wrapper-col="{ span: 18 }" :colon="false">
        <a-form-item :label="$t('admin.user.create.username')">
          <a-input v-decorator="['username', {
            rules: [{ required: true, message: $t('user.userName.required') }]
          }]" :placeholder="$t('admin.user.create.username')" autocomplete="off" />
        </a-form-item>
        <a-form-item :label="$t('admin.user.create.nickname')">
          <a-input v-decorator="['nickname', {
            rules: [{ required: true, message: $t('admin.user.create.nickname.required') }]
          }]" :placeholder="$t('admin.user.create.nickname')" autocomplete="off" />
        </a-form-item>
        <a-form-item :label="$t('admin.user.create.password')">
          <a-input-password v-decorator="['password', {
            rules: [{ required: true, message: $t('user.password.required') }],
            validateTrigger: 'change'
          }]" :placeholder="$t('user.login.password.placeholder')" />
          <password-strength-bar :password="createForm.getFieldValue('password')" @getScore="getScore" />
        </a-form-item>
        <a-form-item :label="$t('admin.user.create.confirm')">
          <a-input-password v-decorator="['confirm', {
            rules: [
              { required: true, message: $t('user.confirm-password.required') },
              { validator: validatePass, message: $t('user.password.twice.msg'), type: 'create' }
            ],
            validateTrigger: 'change'
          }]" :placeholder="$t('user.register.confirm-password.placeholder')" />
        </a-form-item>
        <a-form-item :label="$t('admin.user.create.department')">
          <a-input v-decorator="['department']" :placeholder="$t('admin.user.create.department')" autocomplete="off" />
        </a-form-item>
        <a-form-item :label="$t('admin.user.create.role')">
          <a-select v-decorator="['role', {
            rules: [{ required: true, message: $t('admin.user.create.role.required') }],
            validateTrigger: 'change'
          }]">
            <a-select-option v-for="role in roleList" :key="role.id" :value="role.id">
              {{ role.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item :label="$t('admin.user.create.enable')">
          <a-switch checked-children="启用" un-checked-children="禁用"
                    v-decorator="['enabled', { valuePropName: 'checked', initialValue: true }]"/>
        </a-form-item>
      </a-form>
    </a-modal>

    <a-table :columns="columns" :data-source="data" v-action="'user:list'"
             :row-key="record => record.id" :pagination="pagination"
             :loading="loading" @change="handleTableChange">
      <template slot="nickname" slot-scope="text, record">
        <a-input v-if="record.id === editing" :value="text" style="margin: -5px 0"
                 @change="e => handleChange(e.target.value, record.id, 'nickname')" />
        <template v-else>
          {{ text }}
        </template>
      </template>

      <template slot="department" slot-scope="text, record">
        <a-input v-if="record.id === editing" :value="text" style="margin: -5px 0"
                 @change="e => handleChange(e.target.value, record.id, 'department')"/>
        <template v-else>
          {{ text }}
        </template>
      </template>

      <span slot="roles" slot-scope="roles, record">
        <a-select v-if="record.id === editing" :value="roles[0].id" style="width: 100%"
                  @change="value => handleChange(value, record.id, 'roles')">
          <a-select-option v-for="role in roleList" :key="role.id" :value="role.id">
            {{ role.name }}
          </a-select-option>
        </a-select>
        <a-tag v-else :color="roles[0].role === 'admin' ? 'green' : 'geekblue'">
          {{ roles[0].name }}
        </a-tag>
      </span>

      <span slot="enabled" slot-scope="enabled, record">
        <a-switch checked-children="启用" un-checked-children="禁用"
                  @change="checked => handleChange(checked, record.id, 'enabled')"
                  :checked="Boolean(enabled)" :disabled="record.id !== editing" />
      </span>

      <template slot="time" slot-scope="time" v-if="time">
        {{ time | moment("YYYY-MM-DD HH:mm:ss") }}
      </template>

      <template slot="actions" slot-scope="text, record">
        <div v-if="$store.getters.userInfo.id !== record.id">
          <span v-if="record.id === editing">
            <a-popconfirm title="确认修改？" @confirm="() => update(record.id)" v-action="'user:edit'">
              <a class="actions">保存</a>
            </a-popconfirm>
            <a-popconfirm title="确定放弃修改？" @confirm="() => cancel(record.id)">
              <a class="actions">取消</a>
            </a-popconfirm>
          </span>
          <span v-else>
            <a class="actions" :disabled="editing !== null" @click="() => edit(record.id)" v-action="'user:edit'">编辑</a>
            <a class="actions" :disabled="editing !== null" @click="() => toggleModifyModal(record.id)" v-action="'user:password'">修改密码</a>
            <a-popconfirm title="确定要删除该用户吗？" @confirm="() => deleteUser(record.id)" v-action="'user:delete'">
              <a class="actions" :disabled="editing !== null">删除</a>
            </a-popconfirm>
          </span>
        </div>
        <div v-else>
          <span>
            <router-link :to="{ name: 'Account' }">
              {{ $t('menu.settings.account') }}
            </router-link>
          </span>
        </div>
      </template>
    </a-table>

    <a-modal v-if="$auth('user:password')" :destroy-on-close="true" :visible="modifyModal"
             :title="$t('settings.account.password.modify')"
             @cancel="() => toggleModifyModal()" @ok="confirmModify">
      <a-form :form="modifyForm" :label-col="{ span: 4 }" :wrapper-col="{ span: 18 }" :colon="false">
        <a-form-item :label="$t('settings.account.password.new')">
          <a-input-password v-decorator="['password', {
            rules: [{ required: true, message: $t('user.password.required') }],
            validateTrigger: 'change'
          }]" :placeholder="$t('user.login.password.placeholder')" />
          <password-strength-bar :password="modifyForm.getFieldValue('password')" @getScore="getScore" />
        </a-form-item>
        <a-form-item :label="$t('settings.account.password.confirm')">
          <a-input-password v-decorator="['confirm', {
            rules: [
              { required: true, message: $t('user.confirm-password.required') },
              { validator: validatePass, message: $t('user.password.twice.msg'), type: 'modify' }
            ],
            validateTrigger: 'change'
          }]" :placeholder="$t('user.register.confirm-password.placeholder')" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { getUserList, getRoleList, createUser, updateUser, modifyUserPassword, deleteUser } from '@/api/admin'
import { PasswordStrengthBar } from '@/components'
import md5 from 'md5'

export default {
  name: 'User',
  components: {
    PasswordStrengthBar
  },

  data () {
    return {
      data: [],
      cacheData: [],
      roleList: [],
      pagination: {
        total: 0,
        pageSize: 10,
        showTotal: (total, range) => `${range[0]}-${range[1]} 共 ${total} 条数据`
      },
      createModal: false,
      modifyModal: false,
      createForm: this.$form.createForm(this),
      modifyForm: this.$form.createForm(this),
      loading: false,
      editing: null,
      modifying: null,
      columns: [{
        title: '#',
        dataIndex: 'id',
        width: '50px'
      }, {
        title: '用户名',
        sorter: true,
        dataIndex: 'username',
        width: '100px'
      }, {
        title: '昵称',
        sorter: true,
        dataIndex: 'nickname',
        scopedSlots: { customRender: 'nickname' },
        width: '100px'
      }, {
        title: '部门职位',
        dataIndex: 'department',
        scopedSlots: { customRender: 'department' },
        ellipsis: true
      }, {
        title: '角色',
        dataIndex: 'roles',
        scopedSlots: { customRender: 'roles' }
      }, {
        title: '是否启用',
        filters: [{ text: '启用', value: 1 }, { text: '禁用', value: 0 }],
        dataIndex: 'enabled',
        width: '110px',
        scopedSlots: { customRender: 'enabled' }
      }, {
        title: '创建时间',
        sorter: true,
        dataIndex: 'create_time',
        scopedSlots: { customRender: 'time' },
        width: '160px'
      }, {
        title: '最近登录时间',
        sorter: true,
        dataIndex: 'last_login',
        scopedSlots: { customRender: 'time' },
        width: '160px'
      }, {
        title: '操作',
        dataIndex: 'actions',
        width: '170px',
        scopedSlots: { customRender: 'actions' }
      }]
    }
  },

  mounted () {
    this.fetch()
    this.getRoles()
  },

  methods: {
    handleTableChange (pagination, filters, sorter) {
      this.pagination.current = pagination.current
      this.pagination.pageSize = pagination.pageSize
      const enabled = filters.enabled
      this.fetch({
        page: pagination.current,
        limit: pagination.pageSize,
        sortField: sorter.field,
        sortOrder: sorter.order && sorter.order.slice(0, -3),
        enabled: enabled && enabled.length === 1 ? enabled[0] : null
      })
    },

    fetch (params) {
      this.loading = true
      getUserList({ ...params }).then(({ data }) => {
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.pagination = pagination
        this.data = data.list
        this.cacheData = JSON.parse(JSON.stringify(data.list))
      }).finally(() => {
        this.loading = false
      })
    },

    getRoles () {
      getRoleList().then(({ data }) => {
        this.roleList = data
      })
    },

    validatePass (rule, value, callback) {
      const { getFieldValue } = rule.type === 'create' ? this.createForm : this.modifyForm
      if (value && value !== getFieldValue('password')) {
        callback(this.$t('user.password.twice.msg'))
      }
      callback()
    },

    getScore (score) {
      this.passScore = score
    },

    alertWeakPass (func, values) {
      if (this.passScore !== null && this.passScore < 3) {
        this.$confirm({
          title: this.$t('user.password.strength.msg'),
          okText: '确定',
          okType: 'danger',
          cancelText: '取消',
          onOk: () => func({ ...values }),
          onCancel: () => {}
        })
      } else {
        func({ ...values })
      }
    },

    toggleCreateModal () {
      this.createModal = !this.createModal
    },

    addUser (params) {
      params.password = md5(params.password)
      params.confirm = md5(params.confirm)
      params.enabled = params.enabled ? 1 : 0
      createUser(params).then(() => {
        this.$message.success('创建成功')
        this.fetch()
      }).catch(err => {
        this.$notification['error']({
          message: '创建用户失败',
          description: ((err.response || {}).data || {}).msg || '请求出现错误，请稍后再试',
          duration: 4
        })
      })
      this.toggleCreateModal()
    },

    confirmCreate () {
      this.createForm.validateFields(
        ['username', 'nickname', 'password', 'confirm', 'role', 'department', 'enabled'], { force: true },
        (err, values) => {
          if (!err) {
            this.alertWeakPass(this.addUser, values)
          }
        })
    },

    edit (id) {
      this.editing = id
    },

    handleChange (value, id, column) {
      const newData = [...this.data]
      const target = newData.find(item => id === item.id)
      if (target) {
        if (['nickname', 'department'].includes(column)) {
          target[column] = value
        } else if (column === 'enabled') {
          target[column] = value ? 1 : 0
        } else {
          const role = this.roleList.find(item => value === item.id)
          target[column][0].id = role.id
          target[column][0].role = role.role
          target[column][0].name = role.name
        }
        this.data = newData
      }
    },

    update (id) {
      const target = this.data.find(item => id === item.id)
      const params = {
        id: target.id,
        nickname: target.nickname,
        department: target.department,
        role: target.roles[0].id,
        enabled: target.enabled
      }
      updateUser(params).then(() => {
        this.$message.success('修改成功')
      }).catch(err => {
        this.$notification['error']({
          message: '修改用户信息失败',
          description: ((err.response || {}).data || {}).msg || '请求出现错误，请稍后再试',
          duration: 4
        })
      }).finally(() => {
        this.editing = null
        this.fetch()
      })
    },

    cancel (id) {
      const newData = [...this.data]
      const target = newData.find(item => id === item.id)
      this.editing = null
      if (target) {
        const origin = this.cacheData.find(item => id === item.id)
        target.nickname = origin.nickname
        target.department = origin.department
        target.roles[0] = { ...origin.roles[0] }
        target.enabled = origin.enabled
        this.data = newData
      }
    },

    toggleModifyModal (id = null) {
      this.modifyModal = !this.modifyModal
      this.modifying = id
    },

    modifyPass (params) {
      params.password = md5(params.password)
      params.confirm = md5(params.confirm)
      modifyUserPassword(this.modifying, params).then(() => {
        this.$message.success('修改成功')
      }).catch(err => {
        this.$notification['error']({
          message: '修改用户密码失败',
          description: ((err.response || {}).data || {}).msg || '请求出现错误，请稍后再试',
          duration: 4
        })
      })
      this.toggleModifyModal()
    },

    confirmModify () {
      this.modifyForm.validateFields(['password', 'confirm'], { force: true }, (err, values) => {
        if (!err) {
          this.alertWeakPass(this.modifyPass, values)
        }
      })
    },

    deleteUser (id) {
      deleteUser(id).then(() => {
        this.$message.success('用户已删除')
        this.fetch()
      }).catch(err => {
        this.$notification['error']({
          message: '删除用户失败',
          description: ((err.response || {}).data || {}).msg || '请求出现错误，请稍后再试',
          duration: 4
        })
      })
    }
  }
}
</script>

<style scoped>
.actions {
  margin-right: 8px;
}
</style>
