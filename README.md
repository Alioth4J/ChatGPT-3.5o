# ChatGPT-3.5o
## 开发初衷
ChatGPT-3.5 本身不能读取图片中的文字，但是将 ChatGPT-3.5 与图像识别工具（如 OCR）结合，实现图像到文本的转换，然后由 ChatGPT-3.5 处理转换后的文本信息，使之具有读取和理解图片的能力。  
## 环境与配置
- 目前为 Windows  
- 需要下载 Tesseract-OCR 和 MinIO
- 需要科学上网并在启动类设置 HTTP 监听端口
- `application.yml`中填写相关配置
- sql 语句在`resources/chatgpt-3p5o.sql`中
## 使用注意事项
- 首页：[http://localhost:1003/index.html](http://localhost:1003/index.html)
- 用户注册密码要求为6-16位，同时包含字母和数字，在`UserServiceImpl#isValid`中可进行相关设置
## 时间线
2024/05/10 项目开始  
2024/05/30 在GitHub公开
## 已使用技术
- Spring Boot
- Tess4j
- Spring AI
- Spring Security
- MinIO
- MySQL
- MyBatis
- Redis
