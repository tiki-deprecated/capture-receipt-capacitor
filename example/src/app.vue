<!--
  - Copyright (c) TIKI Inc.
  - MIT license. See LICENSE file in root directory.
  -->

<script setup lang="ts"></script>

<template>
  <header>
    <img alt="TIKI Icon" class="logo" src="./assets/logo.svg" width="125" height="125" />
  </header>

  <main>
    <div class="greetings">
      <h1>Oh, Hi!</h1>
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
