<template>
    <div style="padding:10px;">
        <el-form :model="myQueryForm" ref="myQueryForm" label-width="100px">
            <el-form-item
                    label="选择日期"
                    prop="viewDate">
                <el-date-picker placeholder="选择日期" v-model="myQueryForm.viewDate"  type="daterange"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期" unlink-panels
                                style="width: 100%;" value-format="yyyyMMdd" format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('myQueryForm')">提交</el-button>
                <el-button type="primary" @click="realtimeCheck('myQueryForm')">实时检测</el-button>
                <el-button @click="resetForm('myQueryForm')">重置</el-button>
            </el-form-item>
        </el-form>
        <el-table
                :data="tableData"
                height="400"
                border
                style="width: 100%">
            <el-table-column
                    type="index"
                    label="序号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="ip"
                    label="服务器ip"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="服务器名称"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="visitTime"
                    label="检测时间">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    let initViewData = [new Date().Format('yyyyMMdd'),new Date().Format('yyyyMMdd')];
    module.exports = {
        data() {
            return {
                tableData: [],
                myQueryForm: {
                    viewDate:initViewData
                }
            }
        },
        methods: {
            submitForm(formName) {
                let myParams = {'viewDate': this.myQueryForm.viewDate[0]+"-"+this.myQueryForm.viewDate[1]};
                this.getDataFromServer(myParams);
            },
            realtimeCheck(formName){
                this.tableData=[];
                let myParams = {'viewDate': 'realtimeCheck'};
                this.getDataFromServer(myParams);
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            getDataFromServer(myParams){
                axios
                    .get('/utility/server/checkShareDiskState', {
                        params: myParams
                    })
                    .then(response => (this.tableData = response.data))
                    .catch(function (error) { // 请求失败处理
                        console.log(error);
                    });
            }
        },
        mounted() {
            let myParams = {'viewDate': ''};
            this.getDataFromServer(myParams);
            //console.log(new Date().Format('yyyyMMdd'));
        }
    }
</script>