# UIState
用于动态控制页面显示状态,如加载汇总/加载失败等.

使用该思路不需要在布局文件时考虑各种需要显示的状态问题.状态布局与业务布局完全解耦.需要时添加,想要添加什么样式即可添加什么样式.

代码思路:
1.通过正常状态的view找到其父布局.
2.在两者之间动态增加一层容器布局.
3.状态布局添加到容器布局即可.

其实就是通过代码生成代替了在布局文件中写的过程.
