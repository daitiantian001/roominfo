/**
 * Created by daitian on 2017/7/23.
 */
new Vue({
    el:'#box',
    data:{
      lists:[
          {
              "id": "29941553234894",
              "title": "出售指标房 路桥•金泽华府 南内环西街",
              "style": "3室2 厅2卫 138.84㎡  中层(共27层)",
              "position": "路桥•金泽华府－ 万柏林",
              "name": "王女士",
              "price": "10万",
              "area": "720元/㎡",
              "phone": "13834212259",
              "comefrom": "58同城",
              "createtime": "Fri Jun 02 00:11:17 CST 2017",
              "url": "http://ty.58.com/ershoufang/29941553234894x.shtml"
          }, {
              "id": "30146155139255",
              "title": "晋祠旭泉小区5室二厅二卫南北通透 5室2厅2卫",
              "style": "5室2 厅2卫 200㎡  高层(共6层)",
              "position": "晋祠旭泉小区5室二厅二卫南北通透－ 晋源",
              "name": "赵女士",
              "price": "68万",
              "area": "3400元/㎡",
              "phone": "13803496272",
              "comefrom": "房天下",
              "createtime": "Fri Jun 02 00:10:37 CST 2017",
              "url": "http://ty.58.com/ershoufang/30146155139255x.shtml"
          }
      ]
    },
    created: function () {
        this.fetchData()
    },
    methods:{
        fetchData: function () {
            var self = this;
            axios.get('http://101.200.48.253/all').then(function (response) {
                self.lists = response.data;
            });
        },
        add:function(v){
            window.open(v);
        }
    }
});