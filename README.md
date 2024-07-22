Чтобы скомпилировалось, нужо значение apiKey в package com.plezha.mycurrencyconverter.network. 
Чтобы работали запросы в сеть, оно ещё и должно быть равно API ключу из https://app.freecurrencyapi.com/dashboard.
К примеру, можно создать файл /app/src/main/java/com/plezha/mycurrencyconverter/network/apiKey.kt со следующим содержанием:
```kotlin
package com.plezha.mycurrencyconverter.network

const val apiKey = "fca_live_Q2jMTedAzqRL4BsI1FtQR8knUdzcAuJLYWwS5U0V"
```
Если всё равно не получается забилдить и воспользоватсья, можно написать [мне](https://t.me/plezhaa).

Приложение имеет два экрана: 
- для ввода количества и типа конвертируемой валюты и типа валюты, в которую нужно конвертировать:
<img src="https://github.com/user-attachments/assets/c3705a9f-0ded-4b07-b284-349edc5b2a8b" alt="drawing" width="200"/>

- для отображения результата конвертации:
<img src="https://github.com/user-attachments/assets/201c0f69-7567-4905-98b5-aafbce60c5dc" alt="drawing" width="200"/>

Данные получаются через [бесплатное публичное API](https://app.freecurrencyapi.com) с помощью библиотеки Retrofit.

Результат конвертации обновляется (возможно, вернее сказать ставится) реактивным путём с помощью RxJava3 + LiveData, которые были изучены специально для этого задания.
