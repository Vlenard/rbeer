import authorize from './auth';
import express from 'express';

const router = express.Router();

router.use(express.static('public'));
router.use('/app', authorize as any,  express.static('public/app'));

export default router;