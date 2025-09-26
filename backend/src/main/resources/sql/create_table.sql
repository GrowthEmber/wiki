-- スキーマ作成（存在しない場合のみ）
CREATE SCHEMA IF NOT EXISTS app AUTHORIZATION todo_db_a9qv_user;

-- Todoテーブル
CREATE TABLE IF NOT EXISTS app.todos (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    priority VARCHAR(2) NOT NULL,
    status VARCHAR(1) NOT NULL DEFAULT '0'
);

-- Todoテーブル
COMMENT ON TABLE app.todos IS 'タスクを管理するためのToDoリストテーブル';

-- Todoテーブル
COMMENT ON COLUMN app.todos.id IS 'タスクのユニークID（自動採番）';
COMMENT ON COLUMN app.todos.title IS 'タスクのタイトル';
COMMENT ON COLUMN app.todos.priority IS 'タスクの優先度';
COMMENT ON COLUMN app.todos.status IS 'タスクの状態（0=未着手, 1=進行中, 2=完了）';
