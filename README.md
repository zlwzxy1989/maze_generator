# maze_generator


![タイトル画面](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/0.png "タイトル画面")
![メイン画面](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/2.png "メイン画面")
![設定画面](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/1.png "設定画面")
![迷宮生成](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/recursive_division.gif "迷宮生成")

# このプロジェクトについて
迷宮を生成するデモです。気が向いたら新しい迷宮生成のアルゴリズムを追加するかもしれません。

言語：日本語 中国語 英語

仕様の関係で入り口は左上、出口は右下に固定です。

ソースからビルドすることも可能ですが、`deploy`フォルダーにビルド済みのjarがありますので、ビルドが面倒な方はそちらをお使いください。`maze_generator.jar`をダブルクリックするだけで起動できます。

※Javaで開発していますので、Java環境がなければこちらでインストールしてください：http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

出力した迷宮ファイルはこちらの迷宮解きデモで解けます：https://github.com/zlwzxy1989/maze

※迷宮解きデモを利用する場合、マップが大きいなら、設定からアニメ効果をオフにすることを薦めします。でないと後半がどんどん重くなります・・・

# 主な機能

* サイズ・アルゴリズムを指定して迷宮生成できます

* 生成した迷宮をいろんな条件付きで踏破を試みることができます

# 使い方

基本な流れはこんな感じです：メニューから「迷宮の初期化」->メニューから「迷宮を生成」->手動で迷宮を踏破あるいはメニューから「迷宮を出力」

## メイン画面について

![メイン画面](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/2.png "メイン画面")

| マスタイプ | 説明 |
| :---------- | :--- |
| 青いマス |迷宮の起点です。<br />左上に固定です。 |
| オレンジ色マス | 迷宮の終点です。<br />右下に固定です。 |
| 赤いマス    | 壁です。<br />壁の中に移動するや壁を乗り越えることができません。<br />マウスの場合、クリックしても何も起きません。      |
| 緑色マス    | 現在地です。<br />移動方法については後述します。      |
| 白いマス    | 未探索の通路です。<br />現在地から白いマスに移動する際は直線通路、あるいは最大一回曲がることしかできません。      |
| 浅い灰色マス    | 探索済みの通路です。<br />マウスで操作する場合、未探索通路と違って、クリックするだけで現在地から飛べます。      |
| 灰色マス    | 壁か通路かがわからないマスです。<br />マウスの場合、クリックしても何も起きません。<br />設定の可視範囲を0より大きい値を設定した場合のみ出現します。<br />可視範囲については後述します。      |


## メイン画面の操作について

キーボードの場合、移動は方向キー、あるいはWSADになります。

マウスの場合、マスをクリックするだけです。探索済みマスに移動するのは制限なし、未探索マスには現在地から最大一回まで曲がって届く場合は移動できます。

## 設定について

![設定画面](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/1.png "設定画面")

| 項目 | 説明 |
| :---------- | :--- |
| 迷宮の幅 |そのままの意味です。<br />50以上にするとカクカクするかもしれません・・・|
| 迷宮の高さ | そのままの意味です。<br />50以上にするとカクカクするかもしれません・・・ |
| マスの幅    | 迷宮マスの幅です。<br />迷宮のサイズが大きすぎて画面に収まらない場合は小さくしましょう。      |
| 可視範囲    | 0より大きい値を設定した場合、迷宮生成後、経過したマス（最初は入り口のみ）を中心に、この値の距離以上離れているマスが不可視状態になります（壁か通路なのか分からない）。<br />隣に移動してマスの可視状態を公開することができますが、全体が見える状態でやるより難易度が高いので、オフにしたい場合は0に設定してください。     |
| 壁透視    | 可視範囲が0より大きい場合のみ機能します。<br />チェックした場合、経過したマスを中心に可視範囲内の八方向のマス状態が公開されますが、チェックを外すと、壁の向こうのマス状態が公開されなくなりますので、難易度がさらに上がります。      |
| 夜モード    | 可視範囲が0より大きい場合のみ機能します。<br />チェックした場合、起点と終点を除き、現在地の可視範囲外のマスは全部不可視状態になります。<br />壁透視禁止の夜モードはドM向きです。      |
| 迷宮タイプ    | アルゴリズムについてはこちら：http://www5d.biglobe.ne.jp/~stssk/maze/make.html      |
| アニメ効果    | チェックすると、迷宮生成の過程が見られます。<br />アルゴリズムによって違いますので、興味がありましたら一度見てみてください。      |

# その他

バグを発見したら、ご一報いただけると嬉しいです。

転載・改造は構いませんが、作者名(zlwzxy1989)とこのページのリンクは必ず明記してください。

# maze_generator

![标题画面](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/0_zh.png "标题画面")
![主画面](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/2_zh.png "主画面")
![设置画面](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/1_zh.png "设置画面")

# 关于这个项目
迷宫生成演示工具。可能以后会追加新的迷宫生成方法。

对应语言:日文 中文 英文

迷宫入口固定为左上,出口固定为右下.

虽然可以从源代码编译,怕麻烦的同学请用`deploy`文件夹下准备的编译好的版本。

※程序语言为Java,运行程序需要java环境的支持.如果没有Java环境请从这里下载：http://www.oracle.com/technetwork/cn/java/javase/downloads/jdk8-downloads-2133151-zhs.html

~~※程序界面为日语,只会中文的同学不好意思,请看文字猜一下功能・・・应该不会有太大问题(懒癌发作不想做多语言对应・・・).~~

已添加中文对应

导出的迷宫文件可以用这个工具找出口：https://github.com/zlwzxy1989/maze

※使用上面那个解迷宫工具时,如果迷宫很大,推荐从设定里把动画效果关掉,不然到了后半段会非常卡・・・

# 主要功能

* 根据设置的尺寸和算法生成迷宫

* 根据设置的条件手动走生成的迷宫

# 使用说明

基本流程:菜单「初始化迷宫」->菜单「生成迷宫」->手动走迷宫或菜单「导出迷宫」

## 主画面

![主画面](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/2_zh.png "主画面")

| 格子类型 | 说明 |
| :---------- | :--- |
| 蓝色格子 |迷宫起点。<br />固定在左上。 |
| 橙色格子 | 迷宮终点。<br />固定在右下。 |
| 红色格子    | 墙。<br />墙无法越过,也无法移动到墙里。<br />鼠标操作时,点击也没有任何反应。      |
| 绿色格子    | 当前所在地。<br />移动方法将在后面介绍。      |
| 白色格子    | 还没走到过的路格子。<br />从当前所在地移动到白色格子时,只能走直线或者最多拐弯一次。      |
| 浅灰色格子    | 已走过的路格子。<br />鼠标操作时,和没走到过的路不同,点击即可从当前所在地移动过来。      |
| 灰色格子    | 可能是路或者墙的不明格子。<br />鼠标操作时,点击也没有任何反应。<br />设置里可视范围设置为比0大的值时才会出现。<br />可视范围将在后面介绍。      |

## 主画面的操作方法

键盘操作时,可以用方向键或WSAD移动。

鼠标操作时,点击目标格子即可。点击已探索的格子没有移动限制,点击未探索的格子时,如果从当前所在地最多拐弯一次可以到达则可以移动。

## 设置画面

![设置画面](https://github.com/zlwzxy1989/maze_generator/blob/master/intro_resource/1_zh.png "设置画面")

| 项目 | 说明 |
| :---------- | :--- |
| 迷宮宽度 |设置为50以上可能会卡・・・|
| 迷宮高度 |设置为50以上可能会卡・・・ |
| 格子宽度    | 迷宮格子的宽度。<br />如果迷宫尺寸超出屏幕大小,可以考虑把这个值设置小一点。      |
| 可视范围    | 设置为比0大的值时,迷宫生成后,以通过的路格子为中心（开局只有起点）,比设定的值距离更远的格子会变为不可视状态（无法得知是墙或是路）,移动到附近才会公开格子的信息。<br />会比公开地图的模式要难,想关闭这个模式时请设置为0。     |
| 透视墙壁    | 可视范围大于0时才有效。<br />勾选时,会以通过的路格子为中心公开八方向的周围格子的信息,取消勾选时,墙壁后的格子信息不会被公开.不勾选时难度会比透视模式更高。      |
| 夜晚模式    | 可视范围大于0时才有效。<br />勾选时,起点和终点以外的在当前点的可视范围外的格子将全部变为不可视状态。<br />抖M可以尝试禁止透视墙壁+夜晚模式。   |
| 迷宫类型    | 算法可以参考这里(英文)：https://en.wikipedia.org/wiki/Maze_generation_algorithm      |
| 动画效果    | 勾选时,可以看到迷宫生成动画。<br />不同的算法生成迷宫的过程不同,有兴趣的话可以尝试一下。      |

# 其他

欢迎汇报BUG。

可以随意转载・修改代码,但请保留作者名(zlwzxy1989)和这个页面的URL。

# maze_generator

Sorry for my poor English.Though this application has an English version, but I have not tested whether it runs well in English env.

Feel free to report a bug for this app.

Be free to share/edit the app and source code.But please remain the author name (zlwzxy1989) and the link of this page.Thank you.
