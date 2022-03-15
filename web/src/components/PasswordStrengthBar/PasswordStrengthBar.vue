<template>
  <div class="strength-meter">
    <div class="strength-meter-fill" :data-score="score"></div>
  </div>
</template>

<script>
import zxcvbn from 'zxcvbn'

export default {
  name: 'PasswordStrengthBar',
  props: {
    password: {
      type: String,
      default: null
    }
  },
  computed: {
    score () {
      return this.password ? zxcvbn(this.password, null).score : null
    }
  },
  watch: {
    score (value) {
      this.$emit('getScore', value)
    }
  }
}
</script>

<style scoped>
.strength-meter {
  position: relative;
  height: 3px;
  background: #DDD;
  margin: 5px auto;
  border-radius: 3px;
}

.strength-meter:before {
  left: 20%;
}

.strength-meter:after {
  right: 20%;
}

.strength-meter:before, .strength-meter:after {
  content: '';
  height: inherit;
  background: transparent;
  display: block;
  border-color: #FFF;
  border-style: solid;
  border-width: 0 5px 0 5px;
  position: absolute;
  width: 20%;
  z-index: 10;
}

.strength-meter-fill {
  background: transparent;
  height: inherit;
  position: absolute;
  width: 0;
  border-radius: inherit;
  transition: width 0.5s ease-in-out, background 0.25s;
}

.strength-meter-fill[data-score='0'] {
  background: darkred;
  width: 20%;
}
.strength-meter-fill[data-score='1'] {
  background: orangered;
  width: 40%;
}
.strength-meter-fill[data-score='2'] {
  background: orange;
  width: 60%;
}
.strength-meter-fill[data-score='3'] {
  background: yellowgreen;
  width: 80%;
}
.strength-meter-fill[data-score='4'] {
  background: green;
  width: 100%;
}
</style>
