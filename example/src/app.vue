<script setup lang="ts">
import { ref } from 'vue';
import { login, accounts, scanRetailer, logout, initialize, scanEmail } from './main';
const username = ref<HTMLInputElement>()
const pass = ref<HTMLInputElement>()
const source = ref<HTMLSelectElement>()
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
      <div class="form">
        <input placeholder="username" type="text" ref="username" />
        <input placeholder="password" type="password" ref="pass" />
        <select ref="source">
          <option value="AMAZON">AMAZON</option>
          <option value="GMAIL">AMAZON</option>
        </select>
        <button @click="initialize()">initialize</button>
        <button @click="login(username?.value ?? '', pass?.value ?? '', source?.value ?? '')">Login</button>
        <button @click="accounts()">Accounts</button>
        <button @click="scanEmail()">Scan Email</button>
        <button @click="scanRetailer()">Scan Retailer</button>
        <button @click="logout()">Logout</button>
      </div>
    </div>
  </main>
</template>

<style scoped>
.form{
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}

.form input, .form button, .form select{
  height: 48px;
  margin-bottom: 1.5em;
}

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
