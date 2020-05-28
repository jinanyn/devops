<template>
    <div style="padding:10px;">
        <el-form :inline="true" :model="mySumbitForm" ref="mySumbitForm" label-width="100px"
                 :label-position="labelPosition" size="mini">
            <el-row>
                <el-col :span="16">
                    <el-form-item label="redis键" prop="redisKey">
                        <el-input v-model="mySumbitForm.redisKey" placeholder="输入键" style="width: 600px;"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('mySumbitForm','get')">查询</el-button>
                        <el-button type="primary" @click="submitForm('mySumbitForm','set')">设置</el-button>
                        <el-button @click="resetForm('mySumbitForm')">重置</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="16">
                    <el-form-item label="查询结果" prop="redisVal">
                        <el-input type="textarea" v-model="mySumbitForm.redisVal" :rows="9" placeholder="查询结果" style="width: 600px;"></el-input>
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
                    redisKey: '',
                    redisVal: ''
                }
            }
        },
        methods: {
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            submitForm(formName,operWay) {
                let formData = new FormData();
                formData.append("redisKey", this.mySumbitForm.redisKey);
                formData.append("redisVal", this.mySumbitForm.redisVal);
                formData.append("operWay", operWay);
                axios.post('/utility/redisOperate/operRedisKeyValue', formData,).
                then(response => (this.mySumbitForm.redisVal=response.data.data))
                    .catch(function (error) { // 请求失败处理
                        console.log(error);
                    });
            }
        },
        mounted() {
        }
    }
</script>