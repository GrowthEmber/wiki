<template>
  <div class="container" v-if="displayFlag">
    <h1 class="title">TO DO LIST</h1>

    <!-- 入力フォーム -->
    <div class="submit-form">
      <input
        type="text"
        v-model="newTitle"
        placeholder="タイトルを入力"
        class="input-text"
      />
      <button @click="addTodo" class="btn-add">追加</button>
    </div>

    <!-- TODOリスト -->
    <div class="table-wrapper">
      <table class="todo-table">
        <thead>
          <tr>
            <th class="col-check">✔</th>
            <th class="col-title">タイトル</th>
            <th class="col-status">状態</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="todo in sortedTodos" :key="todo.id">
            <!-- チェックボックス -->
            <td class="text-center">
              <input
                type="checkbox"
                :checked="todo.completed"
                @change="toggleCompleted(todo)"
              />
            </td>

            <!-- タイトル -->
            <td>
              <span :class="{ 'completed': todo.completed }">
                {{ todo.title }}
              </span>
            </td>

            <!-- 状態 -->
            <td class="text-center">
              <span :class="todo.completed ? 'status-complete' : 'status-incomplete'">
                {{ todo.completed ? '完了' : '未完了' }}
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'

const todos = ref([])
const newTitle = ref('')
const displayFlag = ref(false)

// 初回ロード時
onMounted(async () => {
  displayFlag.value = false
  const res = await axios.get('http://localhost:9090/api/todos')
  todos.value = res.data
  displayFlag.value = true
})

// 新規登録
const addTodo = async () => {
  if (!newTitle.value) {
    alert('タイトルを入力してください')
    return
  }

  try {
    await axios.post('http://localhost:9090/api/todos/insert', {
      title: newTitle.value,
      completed: false
    })
    // 再取得
    const reload = await axios.get('http://localhost:9090/api/todos')
    todos.value = reload.data
    newTitle.value = ''
  } catch (error) {
    console.error(error)
    alert('登録に失敗しました')
  }
}

// ソート（未完了 → 完了、ID順）
const sortedTodos = computed(() => {
  return [...todos.value].sort((a, b) => {
    if (a.completed === b.completed) {
      return a.id - b.id
    }
    return a.completed - b.completed
  })
})

// 完了切り替え
const toggleCompleted = async (todo) => {
  const updated = { ...todo, completed: !todo.completed }
  try {
    await axios.put(`http://localhost:9090/api/todos/${todo.id}`, updated)
    const idx = todos.value.findIndex(t => t.id === todo.id)
    if (idx !== -1) {
      todos.value[idx].completed = updated.completed
    }
  } catch (error) {
    console.error(error)
    alert('更新に失敗しました')
  }
}
</script>


<style>
/* 全体レイアウト */
.container {
  margin-left: 10%;
  margin-right: 10%;
  padding: 16px;
  color: #333;
  max-width: 100%;
}

/* 基本デザイン */
.title {
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

.submit-form {
  text-align: center;
  margin-top: 1rem;
}

.submit-form input {
  padding: 0.5rem;
  width: 50%;
  border: 1px solid #ccc;
}

.submit-form button {
  margin-left: 0.5rem;
  padding: 0.5rem 1rem;
  background: #3b82f6;
  color: #fff;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.submit-form button:hover {
  background: #2563eb;
}

.todo-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 0.9rem;
  margin-top: 1.5rem;
}

.todo-table th,
.todo-table td {
  border: 1px solid #ccc;
  padding: 0.5rem;
}

.todo-table th {
  background: #f5f5f5;
}

/* ✅ スマホ用 (600px以下) */
@media (max-width: 600px) {
  .container {
    margin-left: 5%;
    margin-right: 5%;
    padding: 8px; /* 余白をさらに小さく */
  }

  .submit-form input {
    width: 70%;   
    padding: 0.4rem;
  }

  .submit-form button {
    padding: 0.4rem 0.8rem;
    font-size: 0.8rem;
  }

  /* テーブル全体をスクロール可能にする */
  .table-wrapper {
    overflow-x: auto;
  }

  .todo-table {
    font-size: 0.8rem;
    min-width: 350px; /* スクロール用の最低幅 */
  }

  .todo-table th,
  .todo-table td {
    padding: 0.3rem;
  }

  .title {
    font-size: 1.2rem;
  }
}

/* ✅ タブレット用 (600px〜900px) */
@media (min-width: 601px) and (max-width: 900px) {
  .container {
    margin-left: 8%;
    margin-right: 8%;
  }

  .submit-form input {
    width: 60%;
  }
}

/* ✅ PC用 (900px以上) */
@media (min-width: 901px) {
  .container {
    margin-left: 10%;
    margin-right: 10%;
  }

  .submit-form input {
    width: 40%;
  }
}

/* カラム幅 */
.col-check {
  width: 50px;
}
.col-title {
  text-align: left;
}
.col-status {
  width: 100px;
}

/* 完了済みテキスト */
.completed {
  text-decoration: line-through;
  color: #888;
}

/* ステータスバッジ */
.status-complete {
  background-color: #c8e6c9;
  color: #256029;
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
}
.status-incomplete {
  background-color: #fff9c4;
  color: #7a6000;
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
}

.text-center {
  text-align: center;
}
</style>
