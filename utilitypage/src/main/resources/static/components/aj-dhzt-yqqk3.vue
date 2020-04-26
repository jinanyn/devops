<template>
    <div style="padding:10px;">
        <el-form :model="myQueryForm" ref="myQueryForm" label-width="100px">
            <el-form-item
                    label="选择日期"
                    prop="viewDate">
                <el-date-picker placeholder="选择日期"
                                v-model="myQueryForm.viewDate"
                                type="daterange"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期" unlink-panels
                                style="width: 100%;"
                                value-format="yyyyMMdd"
                                format="yyyy-MM-dd">
                </el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('myQueryForm')">提q交</el-button>
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
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="shenqingh"
                    label="申请号"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="examiner"
                    label="审查员"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="rushensj"
                    label="入审时间"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="anjianzt"
                    label="案件状态"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="liuchengzt"
                    label="流程状态"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="anjianywzt"
                    label="案件业务状态"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="codename"
                    label="codename"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="zantingbj"
                    label="暂停标记" width="120">
            </el-table-column>
            <el-table-column
                    prop="guaqibj"
                    label="挂起标记" width="120">
            </el-table-column>
            <el-table-column
                    prop="zhongzhibj"
                    label="终止标记" width="120">
            </el-table-column>
            <el-table-column
                    prop="suodingbj"
                    label="锁定标记" width="120">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    let initViewData = [new Date().Format('yyyyMMdd'), new Date().Format('yyyyMMdd')];
    module.exports = {
        data() {
            return {
                tableData: [],
                myQueryForm: {
                    viewDate: initViewData
                }
            }
        },
        methods: {
            submitForm(formName) {
                let myParams = {'viewDate': this.myQueryForm.viewDate[0] + "-" + this.myQueryForm.viewDate[1]};
                this.getDataFromServer(myParams);
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            getDataFromServer(myParams) {
                axios
                    .get('/utility/dataMonitor/authCaseFivebookMiss', {
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