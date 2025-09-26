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

    <!-- TODOリスト（ドラッグ可能） -->
    <draggable
      v-model="todos"
      item-key="id"
      animation="200"
      tag="ul"
      class="todo-list"
      @end="onDragEnd"
    >
      <template #item="{ element: todo }">
        <li class="todo-item">
          <input
            type="checkbox"
            :checked="todo.status === '2'"
          />
          <span :class="{ completed: todo.status === '2' }" class="todo-title">
            {{ todo.title }}
          </span>
          <span class="priority-badge">優先度: {{ todo.priority }}</span>
          <span
            :class="getStatusClass(todo.status)"
            class="status-badge"
            @click="toggleCompleted(todo)"
          >
            {{ getStatusLabel(todo.status) }}
          </span>
        </li>
      </template>
    </draggable>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/axios';
import draggable from 'vuedraggable'

const todos = ref([])
const newTitle = ref('')
const displayFlag = ref(false)

// 初回ロード時
onMounted(async () => {
  displayFlag.value = false
  await fetchTodos()
  displayFlag.value = true
})

// Todo取得（未着手→進行中→完了、優先度順）
const fetchTodos = async () => {
  const res = await api.get('/todos')
  todos.value = res.data
//  .sort((a, b) => {
//    if (a.status === b.status) {
//      return a.priority - b.priority
//    }
//    return a.status.localeCompare(b.status)
//  })
}

// 新規登録
const addTodo = async () => {
  if (!newTitle.value) {
    alert('タイトルを入力してください')
    return
  }

  try {
    // 新しい優先度 = 最大 +1
    const maxPriority = todos.value.length
      ? Math.max(...todos.value.map(t => t.priority))
      : 0

    await api.post('/todos/insert', {
      title: newTitle.value,
      status: "0", // 未着手
      priority: maxPriority + 1
    })
    await fetchTodos()
    newTitle.value = ''
  } catch (error) {
    console.error(error)
    alert('登録に失敗しました')
  }
}

// ステータス切替
const toggleCompleted = async (todo) => {
  let nextStatus = "0"
  if (todo.status === "0") nextStatus = "1"
  else if (todo.status === "1") nextStatus = "2"
  else if (todo.status === "2") nextStatus = "0"

  const updated = { ...todo, status: nextStatus }
  try {
    await api.put(`/todos/${todo.id}`, updated)
    await fetchTodos()
  } catch (error) {
    console.error(error)
    alert('更新に失敗しました')
  }
}

// ドラッグ終了時に優先度を更新
const onDragEnd = async () => {
  try {
    // 並び順を優先度に反映
    const updatedTodos = todos.value.map((todo, index) => ({
      ...todo,
      priority: index + 1
    }))
    todos.value = updatedTodos
    // バックエンドに一括更新
    await api.put('/todos/reorder', updatedTodos)
  } catch (error) {
    console.error(error)
    alert('並び順の更新に失敗しました')
  }
}

// ステータスラベル
const getStatusLabel = (status) => {
  if (status === "0") return "未着手"
  if (status === "1") return "進行中"
  if (status === "2") return "完了"
  return "不明"
}

// ステータスに応じたclass
const getStatusClass = (status) => {
  if (status === "0") return "status-not-started"
  if (status === "1") return "status-in-progress"
  if (status === "2") return "status-complete"
  return ""
}
</script>


<style>
.container {
  margin: 10%;
  padding: 16px;
  color: #333;
  max-width: 100%;
}

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

.todo-list {
  list-style: none;
  padding: 0;
  margin-top: 1.5rem;
}

.todo-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #ccc;
  padding: 0.5rem;
  background: #fff;
  background-color: #FFE4C4;
}

.todo-title {
  flex: 1;
  margin-left: 8px;
}

.completed {
  text-decoration: line-through;
  color: #888;
}

.status-badge {
  margin-left: 10px;
}

.status-complete {
  background-color: #c8e6c9;
  color: #256029;
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
}

.status-not-started {
  background-color: #ffcdd2; /* 薄い赤 */
  color: #b71c1c;            /* 濃い赤 */
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
}

.status-in-progress {
  background-color: #fff9c4; /* 薄い黄色 */
  color: #f57f17;            /* 濃いオレンジ */
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
}

.status-complete {
  background-color: #c8e6c9; /* 薄い緑 */
  color: #256029;            /* 濃い緑 */
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
}

.list-move {
  transition: transform 0.6s ease, opacity 0.6s ease;
}

.list-enter-active, .list-leave-active {
  transition: all 0.6s ease;
}

.list-enter-from, .list-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

/* スマホ (〜600px) */
@media (max-width: 600px) {
  .container {
    margin-left: 5%;
    margin-right: 5%;
    padding: 8px;
  }

  .submit-form input {
    width: 70%;
    padding: 0.4rem;
  }

  .submit-form button {
    padding: 0.4rem 0.8rem;
    font-size: 0.8rem;
  }

  .todo-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .todo-title {
    margin: 6px 0;
  }
}

/* タブレット (601px〜1023px) */
@media (min-width: 601px) and (max-width: 1023px) {
  .container {
    margin: 0 auto;
    max-width: 90%;
    padding: 12px;
  }

  .submit-form input {
    width: 75%;
    padding: 0.6rem;
  }

  .submit-form button {
    padding: 0.6rem 1rem;
    font-size: 0.9rem;
  }

  .todo-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}

/* PC (1024px〜) */
@media (min-width: 1024px) {
  .container {
    margin: 0 auto;
    max-width: 1000px;
    padding: 16px;
  }

  .submit-form input {
    width: 80%;
    padding: 0.8rem;
    font-size: 1rem;
  }

  .submit-form button {
    padding: 0.8rem 1.2rem;
    font-size: 1rem;
  }

  .todo-item {
    display: grid;
    grid-template-columns: 48px 1fr 100px 120px;
    gap: 12px;
    padding: 12px;
  }

  .item-left { grid-column: 1 / span 2; }
  .item-right { grid-column: 3 / span 2; }
}

</style>
