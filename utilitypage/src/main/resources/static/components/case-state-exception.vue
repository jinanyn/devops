<template>
    <div style = "padding:10px;">
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
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            getDataFromServer(myParams){
                axios
                    .get('/utility/dataMonitor/caseStateException', {
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