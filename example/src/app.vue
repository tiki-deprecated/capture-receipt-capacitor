<script setup lang="ts">
import { ref } from 'vue';
import { login, accounts, scan, logout, initialize } from './main';
const username = ref<HTMLInputElement>()
const password = ref<HTMLInputElement>()
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
        <label for="">Choose the Account</label>
        <select ref="source">
          <option value="AMAZON" label="Amazon" />
          <option value="BESTBUY" label="BESTBUY" />
          <option value="DOOR_DASH" label="DOOR DASH" />
          <option value="STARBUCKS" label="STARBUCKS" />
          <option value="GMAIL" label="Gmail" />
          
        </select>
        <input placeholder="username" type="text" ref="username" />
        <input placeholder="password" type="password" ref="password" />
        <button @click="initialize()">initialize</button>
        <button @click="login(username?.value ?? '', password?.value ?? '', source?.value ?? '')">Login</button>
        <button @click="accounts()">Accounts</button>
        <button @click="scan()">Scan</button>
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
  width: 100%;
  border: none;
  background-color: #ebebeb;
  border-radius: 0.7em;
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
