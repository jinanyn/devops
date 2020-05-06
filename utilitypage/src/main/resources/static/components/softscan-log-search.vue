<template>
    <div style="padding:10px;">
        <el-form :inline="true" :model="mySumbitForm" ref="mySumbitForm" label-width="100px"
                 :label-position="labelPosition" size="mini">
            <el-row>
                <el-col :span="16">
                    <el-form-item label="申请号" prop="shenqingh">
                        <el-input v-model="mySumbitForm.shenqingh" placeholder="输入申请号" style="width: 600px;"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('mySumbitForm')">查询新型相关通知书</el-button>
                        <el-button @click="resetForm('mySumbitForm')">重置</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <el-table
                :data="tableData"
                height="200"
                border
                style="width: 100%">
            <el-table-column
                    type="index"
                    label="序号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="rid"
                    label="通知书主键"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="shenqingh"
                    label="申请d号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="tongzhislx"
                    label="通知书类型"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="fid"
                    label="fid">
            </el-table-column>
            <el-table-column
                    prop="wenjiancflj"
                    label="源文件路径">
            </el-table-column>
            <el-table-column
                    prop="tuxingwjcflj"
                    label="扫描件路径">
            </el-table-column>
            <el-table-column
                    prop="tongzhiszt"
                    label="通知书状态">
            </el-table-column>
        </el-table>
        <el-form :inline="true" :model="myForm" ref="myForm" label-width="100px"
                 :label-position="labelPosition" size="mini">
            <el-row>
                <el-col :span="16">
                    <el-form-item label="软扫批次号" prop="ruansaopc">
                        <el-input v-model="myForm.ruansaopc" placeholder="输入软扫批次号" style="width: 600px;"></el-input>
                    </el-form-item>
                    <el-form-item label="距离当前时间分钟数" prop="minutes">
                        <el-input v-model="myForm.minutes" placeholder="输入距ss离当前时间分钟数" style="width: 600px;"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item>
                        <el-button type="primary" @click="submitLogForm('myForm')">搜索日志文件</el-button>
                        <el-button @click="resetForm('myForm')">重置</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="16">
                    <el-form-item label="查询结果" prop="logPath">
                        <el-input type="textarea" v-model="myForm.logPath" :rows="9" placeholder="查找结果" style="width: 600px;"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
    module.exports = {
        data() {
            return {
                labelPosition: 'left',
                mySumbitForm: {
                    shenqingh: ''
                },
                myForm:{
                    ruansaopc:'',
                    logPath:'',
                    minutes:''
                },
                tableData:[]
            }
        },
        methods: {
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            submitForm(formName) {
                axios.get('/utility/noticeOperate/queryNoticeList?shenqingh='+this.mySumbitForm.shenqingh).
                then(response => (this.tableData=response.data))
                    .catch(function (error) { // 请求失败处理
                        console.log(error);
                    });
            },
            submitLogForm(formName){
                let myParams = {
                    minutes: this.myForm.minutes,
                    ruansaopc:this.myForm.ruansaopc
                };
                //console.log(JSON.stringify(myParams));
                let config = {
                    'Content-Type': 'application/json;charset=UTF-8'
                };
                //console.log(config);
                axios.post('/utility/noticeOperate/findSoftscanLog', myParams, config).then( (response) => {
                        console.log(response.data.logPath);
                        this.myForm.logPath = response.data.logPath;
                    }).catch( (error) => {
                        console.log(error);
                    });
            }
        },
        mounted() {
        }
    }
</script>