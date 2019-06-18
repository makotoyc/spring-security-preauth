# boot-security-preauth

Spring Security Testを使用して、単体テストを作成するサンプル

## 認証方式

認証の方式は、PreAuthenticatedAuthenticationを使用

* 外部システムのSSOなどで認証はすでに終えている前提で、ヘッダーからプリンシパルとクレデンシャルを取得する

## 目的

PreAuthenticatedAuthenticationでも、単体テストではWithMockUserを使用することができることを示す

## そのほか

* 認可が失敗する場合は403ではなく、400のJSONを返す(例外をそのまま返したくないため)
