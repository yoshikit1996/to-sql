# to-sqlite

## デモ

```
$ cat sample.json
{"stringValue": "aaa", "intValue": 1}
{"stringValue": "bbb", "intValue": 2}%
$ to-sqlite --input=sample.json --output=sample.db 
$ sqlite3 sample.db
SQLite version 3.28.0 2019-04-15 14:49:49
Enter ".help" for usage hints.
sqlite> select * from main;
aaa|1.0
bbb|2.0
```

## アーキテクチャ

3層 + ドメインモデル:  
(1) UI(プレゼンテーション層)  
(2) アプリケーション層  
(3) インフラストラクチャ層

各層は、下位層へ依存していいが、上位層へ依存してはいけない。
ドメインモデルに関しては、アプリケーション層とインフラ層から依存されてもいいが、ドメインモデル自体は他の層に依存してはならない。

## ビルド方法

```
$ sbt universal:packageBin
```
