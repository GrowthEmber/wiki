<template>
<div id="app">
    <nav>
      <router-link to="/todos">Todo一覧</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script setup>
import TodoList from './components/TodoList.vue'
</script>
<style>
body {
  background-color: #FFE4C4;
  margin: 0;
  font-family: Arial, sans-serif;
}

#app {
  display: block !important;
  max-width: 1280px;
  margin: 0 auto;
  padding: 2rem;
}
</style>