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
                        <el-button type="primary" @click="submitForm('mySumbitForm')">查询</el-button>
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
            submitForm(formName) {
                let formData = new FormData();
                formData.append("redisKey", this.mySumbitForm.redisKey);
                axios.post('/utility/redisOperate/getRedisKeyValue', formData,).
                then(response => (console.log(response.data)))
                    .catch(function (error) { // 请求失败处理
                        console.log(error);
                    });
            }
        },
        mounted() {
        }
    }
</script>