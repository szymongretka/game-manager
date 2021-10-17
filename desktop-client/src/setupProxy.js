const {createProxyMiddleware} = require('http-proxy-middleware');
const proxy = require('http-proxy-middleware');
module.exports = function (app) {
    app.use(createProxyMiddleware('/',
        {
            target: 'http://localhost:8082',
            secure: false,
            changeOrigin: true
        }
    ));
}
