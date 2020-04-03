<template>
    <div style="padding:10px;">
        <el-form :model="myQueryForm" ref="myQueryForm" label-width="100px">
            <el-form-item
                    label="选择日期"
                    prop="age">
                <el-date-picker type="date" placeholder="选择日期" v-model="myQueryForm.viewDate"
                                style="width: 100%;" value-format="yyyyMMdd" format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('myQueryForm')">提交</el-button>
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
                    prop="shenqingh"
                    label="申请号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="examiner"
                    label="审查员"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="comments"
                    label="备注">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    module.exports = {
        data() {
            return {
                tableData: [],
                myQueryForm: {
                    viewDate: new Date()
                }
            }
        },
        methods: {
            submitForm(formName) {
                let myParams = {'viewDate': this.myQueryForm.viewDate};
                this.getDataFromServer(myParams);
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            getDataFromServer(myParams){
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
        }
    }
</script>