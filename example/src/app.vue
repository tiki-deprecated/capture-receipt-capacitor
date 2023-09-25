<script setup lang="ts">
import { ref } from 'vue';
import { login, accounts, scan, logout, initialize } from './main';
const username = ref<HTMLInputElement>()
const pass = ref<HTMLInputElement>()
</script>

<template>
  <header>
    <img alt="TIKI Icon" class="logo" src="./assets/logo.svg" width="125" height="125" />
    <style>
      .greetings {
        display:flex; 
        flex-direction:column; 
        justify-content: space-evenly;
      }

      .greetings input, .greetings button{
        margin-top: 10px;
        height: 48px;
      }
    </style>
  </header>

  <main>
    <div class="greetings" style="">
      <h1>Oh, Hi!</h1>
      <input type="text" ref="username" />
      <input type="password" ref="pass" />
      <button @click="initialize()">initialize</button>
      <button @click="login(username?.value ?? '', pass?.value ?? '', 'AMAZON')">Login</button>
      <button @click="accounts()">Accounts</button>
      <button @click="scan()">Scan</button>
      <button @click="logout()">Logout</button>
    </div>
  </main>
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

.greetings h1 {
  font-weight: 500;
  font-size: 1.25em;
  line-height: 1.75em;
  color: #00b272;
}

.greetings h3 {
  font-size: 1.25em;
  line-height: 1.75em;
  font-weight: normal;
}

.greetings h1,
.greetings h3,
.greetings p {
  text-align: center;
}
</style>
