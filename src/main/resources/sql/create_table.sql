-- スキーマ作成（存在しない場合のみ）
CREATE SCHEMA IF NOT EXISTS app AUTHORIZATION wiki_user;

-- Todoテーブル
CREATE TABLE IF NOT EXISTS todos (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    completed BOOLEAN DEFAULT FALSE
);

-- Todoテーブル
COMMENT ON TABLE todos IS 'タスクを管理するためのToDoリストテーブル';

-- Todoテーブル
COMMENT ON COLUMN todos.id IS 'タスクのユニークID（自動採番）';
COMMENT ON COLUMN todos.title IS 'タスクのタイトル';
COMMENT ON COLUMN todos.completed IS '完了フラグ（TRUE=完了、FALSE=未完了）';
