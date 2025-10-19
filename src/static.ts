import auth from './auth';
import express from 'express';

const router = express.Router();

router.use(express.static('public'));
router.use('/app', auth.authorize,  express.static('public/app'));

export default router;