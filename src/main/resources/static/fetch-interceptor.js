// 拦截所有的 fetch 请求并添加 Authorization 头部
window.fetch = (function (originalFetch) {
    return function (url, options) {
        // 如果未提供选项，则创建一个新对象
        options = options || {};
        // 如果未提供头部，则创建一个新对象
        options.headers = options.headers || {};
        // 如果本地存储中存在令牌，则包含带有令牌的授权头
        var token = localStorage.getItem('token');
        var tokenHead = localStorage.getItem('tokenHead');
        if (token && tokenHead) {
            options.headers['Authorization'] = tokenHead + ' ' + token;
        }
        // 使用修改后的选项调用原始 fetch 函数
        return originalFetch(url, options);
    };
})(window.fetch);
