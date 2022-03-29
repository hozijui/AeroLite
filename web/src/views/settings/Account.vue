<template>
  <div>
    <a-row type="flex" justify="start">
      <a-col :order="isMobile ? 2 : 1" :md="24" :lg="16">
        <a-form :form="infoForm" @submit="updateInfo" v-action="'account:view'"
                :label-col="{ span: 3 }" :wrapper-col="{ span: 18 }" :colon="false">
          <a-form-item :label="$t('settings.account.username')">
            <a-input :value="userInfo.username" disabled />
          </a-form-item>
          <a-form-item :label="$t('settings.account.password')" v-action="'account:password'">
            <a-button type="link" id="modify-password-btn" @click="toggleModal">
              {{ $t('settings.account.password.modify') }}
            </a-button>
          </a-form-item>
          <a-form-item :label="$t('settings.account.nickname')">
            <a-input v-decorator="['nickname', {
              rules: [{ required: true, message: $t('settings.account.nickname.required') }],
              validateTrigger: 'change'
            }]" autocomplete="off" />
          </a-form-item>
          <a-form-item :label="$t('settings.account.department')">
            <a-input v-decorator="['department']" autocomplete="off" />
          </a-form-item>
          <a-form-item :label="$t('settings.account.role')">
            <a-select mode="tags" :defaultValue="userInfo.roles.map(role => role.name)" disabled>
            </a-select>
          </a-form-item>
          <a-form-item :label="$t('settings.account.status')">
            <a-switch :checked="userInfo.enabled === 1" checked-children="启用" un-checked-children="禁用" disabled />
          </a-form-item>
          <a-form-item :wrapper-col="{ offset: 3 }">
            <a-button type="primary" htmlType="submit" v-action="'account:edit'"
                      :loading="updateBtn"
                      :disabled="updateBtn"
            >{{ $t('settings.account.update') }}</a-button>
          </a-form-item>
        </a-form>
      </a-col>
    </a-row>

    <a-modal v-if="$auth('account:password')" :destroy-on-close="true"
             :title="$t('settings.account.password.modify')" :visible="showModal"
             :confirm-loading="modifyBtn" @cancel="toggleModal" @ok="confirmModify">
      <a-form :form="modifyForm" :label-col="{ span: 4 }" :wrapper-col="{ span: 18 }" :colon="false">
        <a-form-item :label="$t('settings.account.password.old')">
          <a-input-password v-decorator="['origin', {
            rules: [{ required: true, message: $t('user.password.required') }],
            validateTrigger: 'change'
          }]" :placeholder="$t('user.login.password.placeholder')" />
        </a-form-item>
        <a-form-item :label="$t('settings.account.password.new')">
          <a-input-password v-decorator="['password', {
            rules: [
              { required: true, message: $t('user.password.new.required') },
              { min: 6, message: $t('user.register.password.popover-message') }
            ],
            validateTrigger: 'change'
          }]" :placeholder="$t('user.register.password.placeholder')" />
          <password-strength-bar :password="modifyForm.getFieldValue('password')" @getScore="getScore" />
        </a-form-item>
        <a-form-item :label="$t('settings.account.password.confirm')">
          <a-input-password v-decorator="['confirm', {
            rules: [
              { required: true, message: $t('user.confirm-password.required') },
              { validator: validatePass, message: $t('user.password.twice.msg') }
            ],
            validateTrigger: 'change'
          }]" :placeholder="$t('user.register.confirm-password.placeholder')" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { PasswordStrengthBar } from '@/components'
import { baseMixin } from '@/store/app-mixin'
import { updateInfo, modifyPassword } from '@/api/account'
import { welcome } from '@/utils/util'
import md5 from 'md5'

export default {
  name: 'Account',
  components: {
    PasswordStrengthBar
  },
  mixins: [baseMixin],
  data () {
    return {
      showModal: false,
      infoForm: this.$form.createForm(this),
      modifyForm: this.$form.createForm(this),
      updateBtn: false,
      modifyBtn: false,
      passScore: null
    }
  },

  computed: {
    userInfo () {
      return this.$store.getters.userInfo
    }
  },

  mounted () {
    this.infoForm.setFieldsValue({
      'nickname': this.userInfo.nickname,
      'department': this.userInfo.department
    })
  },

  methods: {
    updateInfo (e) {
      e.preventDefault()
      this.updateBtn = true
      this.infoForm.validateFields(['nickname', 'department'], { force: true }, (err, values) => {
        if (!err) {
          const updateParams = { ...values, id: this.userInfo.id }
          updateInfo(updateParams).then(response => {
            const result = response.data
            this.$store.commit('SET_INFO', result)
            this.$store.commit('SET_NICKNAME', { nickname: result.nickname, welcome: welcome() })
            this.$store.commit('SET_DEPARTMENT', result.department)
            this.$message.success('修改成功')
          }).catch(err => {
            this.$notification['error']({
              message: '修改失败',
              description: ((err.response || {}).data || {}).msg || '请求出现错误，请稍后再试',
              duration: 4
            })
          }).finally(() => {
              this.updateBtn = false
          })
        } else {
          setTimeout(() => {
            this.updateBtn = false
          }, 600)
        }
      })
    },

    toggleModal () {
      this.showModal = !this.showModal
    },

    getScore (score) {
      this.passScore = score
    },

    validatePass (rule, value, callback) {
      const { getFieldValue } = this.modifyForm
      if (value && value !== getFieldValue('password')) {
        callback(this.$t('user.password.twice.msg'))
      }
      callback()
    },

    modifyPass (params) {
      params.origin = md5(params.origin)
      params.password = md5(params.password)
      params.confirm = md5(params.confirm)
      modifyPassword(params).then(() => {
        this.$message.success('修改成功')
      }).catch(err => {
        this.$notification['error']({
          message: '修改密码失败',
          description: ((err.response || {}).data || {}).msg || '请求出现错误，请稍后再试',
          duration: 4
        })
      }).finally(() => {
        this.modifyBtn = false
      })
      this.toggleModal()
    },

    confirmModify () {
      this.modifyBtn = true
      this.modifyForm.validateFields(['origin', 'password', 'confirm'], { force: true }, (err, values) => {
        if (!err) {
          if (this.passScore !== null && this.passScore < 3) {
            this.$confirm({
              title: this.$t('user.password.strength.msg'),
              okText: '确定',
              okType: 'danger',
              cancelText: '取消',
              onOk: () => this.modifyPass({ ...values }),
              onCancel: () => { this.modifyBtn = false }
            })
          } else {
            this.modifyPass({ ...values })
          }
        } else {
          setTimeout(() => {
            this.modifyBtn = false
          }, 600)
        }
      })
    }
  }
}
</script>

<style scoped>
#modify-password-btn {
  float: right;
}
</style>
