-- スキーマ作成（存在しない場合のみ）
CREATE SCHEMA IF NOT EXISTS app AUTHORIZATION ${DB_USER};

-- Todoテーブル
CREATE TABLE IF NOT EXISTS todos (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    priority VARCHAR(2) NOT NULL,
    status VARCHAR(1) NOT NULL DEFAULT '0'
);

-- Todoテーブル
COMMENT ON TABLE todos IS 'タスクを管理するためのToDoリストテーブル';

-- Todoテーブル
COMMENT ON COLUMN todos.id IS 'タスクのユニークID（自動採番）';
COMMENT ON COLUMN todos.title IS 'タスクのタイトル';
COMMENT ON COLUMN todos.priority IS 'タスクの優先度';
COMMENT ON COLUMN app.todos.status IS 'タスクの状態（0=未着手, 1=進行中, 2=完了）';
